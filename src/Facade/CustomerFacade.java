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
     * logIn - get customer id field
     */
    public synchronized Customer logIn(String email, String password) {

        Customer customer= null;
        try {
            customer = customersDAO.isCustomerExists(email, password);
        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerId =customer.getId();
        return customer;

    }


    //o לא ניתן לרכוש את אותו הקופון יותר מפעם אחת.V
    //o לא ניתן לרכוש את הקופון אם הכמות שלו היא 0.V
        //o לא ניתן לרכוש את הקופון אם תאריך התפוגה שלו כבר הגיע.
        //o לאחר הרכישה יש להוריד את הכמות במלאי של הקופון ב-1.
    /**
     * purchaseCoupon - will allow the purchase if and only if the coupon
     * wasn't purchased already by this client
     * also will check if coupon is not expired by endDate or by the available amount
     */
        public synchronized void purchaseCoupon(int couponId) throws CouponSystemException {
            if(!couponsDAO.doesCustomerPurchaseExist(this.customerId,couponId)){

                if(couponsDAO.getOneCoupon(couponId).getAmount()>0
                && couponsDAO.getOneCoupon(couponId).getEndDate().isBefore(LocalDateTime.now())) {

                    couponsDAO.addCouponPurchase(this.customerId, couponId);
                }else{
                    System.out.println("can't purchase this coupon expired ");

                }
            }else{
                System.out.println("you already have this coupon ");
            }

    }


    public  ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException {
         return customersDAO.getCustomerCoupons(this.getCustomerId());
    }


    public  ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException {
        return customersDAO.getCustomerCoupons(maxPrice,this.getCustomerId());
    }

    public synchronized ArrayList<Coupon> getCustomerCoupons(Category couponCategory) throws CouponSystemException {
        return customersDAO.getCustomerCoupons(couponCategory,this.getCustomerId());
    }


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