package DAO;

import Beans.Coupon;
import Exceptions.CouponSystemException;

import java.util.ArrayList;

public interface CouponsDAO {

    int addCoupon(Coupon coupon) throws CouponSystemException;
    void updateCoupon(Coupon coupon) throws CouponSystemException;
    void deleteCoupon(int couponId) throws CouponSystemException;
    ArrayList<Coupon> getAllCoupons() throws CouponSystemException;
    Coupon getOneCoupon(int couponId) throws CouponSystemException;
    void addCouponPurchase(int customerId , int couponId) throws CouponSystemException;
    void deleteCouponPurchase(int customerId , int couponId);


}
