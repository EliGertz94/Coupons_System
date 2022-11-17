package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import Exceptions.CouponSystemException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;

public class CustomerFacade  extends ClientFacade {


    private int customerId;

    /**
     * logIn - returns boolean according to login status
     * will instantiate  the customerId field
     */
    public  boolean logIn(String email, String password) {

        try {
            customerId =customersDAO.customerByLogIn(email,password).getId();

            return customersDAO.isCustomerExists(email, password);

        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    /**
     * purchaseCoupon - will allow the purchase if and only if the coupon
     * wasn't purchased already by this client
     * also will check if coupon is not expired by endDate or by the available amount
     */
        public  void purchaseCoupon(int couponId) throws CouponSystemException {
            if(!couponsDAO.doesCustomerPurchaseExist(this.customerId,couponId)){

                if(couponsDAO.getOneCoupon(couponId).getAmount()>0
                && couponsDAO.getOneCoupon(couponId).getEndDate().isAfter(LocalDateTime.now())) {

                    couponsDAO.addCouponPurchase(this.customerId, couponId);
                }else{
                    System.out.println("can't purchase this coupon expired ");

                }
            }else{
                System.out.println("you already have this coupon ");
            }

    }


    /**
     * getCustomerCoupons - get the coupons of the customer by the customerId
     */
    public  ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException {
         return customersDAO.getCustomerCoupons(this.getCustomerId());
    }


    /**
     * getCustomerCoupons - get the coupons of the customer by the customerId and macPrice
     */
    public  ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException {
        return customersDAO.getCustomerCoupons(maxPrice,this.getCustomerId());
    }

    /**
     * getCustomerCoupons - get the coupons of the customer by the customerId and category
     */
    public synchronized ArrayList<Coupon> getCustomerCoupons(Category couponCategory) throws CouponSystemException {
        return customersDAO.getCustomerCoupons(couponCategory,this.getCustomerId());
    }

    /**
     * getCustomerDetails - returns an object of a customer according to the logged in id
     * adding all the coupons of the customer to the list of the object
     */
    public  Customer getCustomerDetails() throws CouponSystemException{
        try {
            Customer customer=  customersDAO.getOneCustomer(this.getCustomerId());
            customer.setCoupons(customersDAO.getCustomerCoupons(this.getCustomerId()));
            return customer;
        } catch (CouponSystemException e) {
            throw new CouponSystemException("getCustomerDetails error at CustomerFacade",e);
        }

    }
    /**
     * getCustomerId - get customer id field
     */

    public int getCustomerId() {
        return customerId;
    }
}