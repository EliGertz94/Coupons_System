package DAO;

import Beans.Coupon;

import java.util.ArrayList;

public interface CouponsDAO {

    void addCoupon(Coupon coupon);
    void updateCoupon(Coupon coupon);
    void deleteCoupon(int couponId);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponId);
    void addCouponPurchase(int customerId , int couponId);
    void deleteCouponPurchase(int customerId , int couponId);


}
