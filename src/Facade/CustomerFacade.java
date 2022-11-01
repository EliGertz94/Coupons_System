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
        public synchronized void purchaseCoupon(int couponId) throws CouponSystemException {
            if(!couponsDAO.doesCustomerPurchaseExist(this.customerId,couponId)){

                if(couponsDAO.getOneCoupon(couponId).getAmount()>0
                && couponsDAO.getOneCoupon(couponId).getEndDate().isBefore(LocalDateTime.now())) {

                    couponsDAO.addCouponPurchase(this.customerId, couponId);
                }else{
                   // System.out.println("dfsdfsdf");
                }
            }else{
                System.out.println("already purchase this coupon");
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


    public synchronized Customer getCustomerDetails() throws CouponSystemException{
        try {
            return customersDAO.getOneCustomer(this.getCustomerId());
        } catch (CouponSystemException e) {
            throw new CouponSystemException("getCustomerDetails error at CustomerFacade",e);
        }

    }

    public int getCustomerId() {
        return customerId;
    }
}