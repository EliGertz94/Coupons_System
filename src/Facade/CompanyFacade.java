package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import ConnectionPoolRelated.ConnectionPool;
import Exceptions.CouponSystemException;

import java.sql.*;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade{

    private int companyId;


    /**
     * logIn - return boolean if email and password are correct
     */
    public boolean logIn(String email, String password) throws CouponSystemException {

        try {
            this.companyId=companiesDAO.companyByLogin(email,password).getId();
        boolean result  =   companiesDAO.isCompanyExists(email,password);
            return result;
        } catch (CouponSystemException e) {
           throw new CouponSystemException("logIn at CompanyFacade",e);
        }
    }

    /**
     * addCoupon - will add a coupon record with unique title
     */
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        if(coupon.getCompanyId() == this.companyId) {
            if (!couponsDAO.uniqueTitleByCompany(coupon.getCompanyId(), coupon.getTitle())) {
                couponsDAO.addCoupon(coupon);
            } else {
                throw new CouponSystemException("this title for a coupon exist already");
            }
        }

    }


    public synchronized void updateCoupon(Coupon coupon) throws CouponSystemException {

        if(coupon.getCompanyId() != this.companyId) {
            throw new CouponSystemException("updateCoupon error at CompanyFacade");
        }
            couponsDAO.updateCoupon(coupon);
    }


    public void deleteCoupon(int couponId) throws CouponSystemException {
        Coupon companyCoupon =  couponsDAO.getOneCoupon(couponId);

        if(companyCoupon.getCompanyId() != this.getCompanyId()){
           throw new CouponSystemException("deleteCoupon at CompanyFacade - no permission to delete coupon");
        }
        couponsDAO.deleteCoupon(couponId);
    }

    public  ArrayList<Coupon> getAllCompanyCoupons() throws CouponSystemException {
       return companiesDAO.getAllCompanyCoupons(this.getCompanyId());
    }

    public synchronized ArrayList<Coupon> getAllCompanyCoupons(Category category) throws CouponSystemException {
        return companiesDAO.getAllCompanyCoupons(category,this.getCompanyId());
    }


    public synchronized ArrayList<Coupon> getAllCompanyCoupons(double maxPrice) throws CouponSystemException {
        return companiesDAO.getAllCompanyCoupons(maxPrice,this.getCompanyId());
    }
    public synchronized Company getCompanyDetails( ) throws CouponSystemException {

       return companiesDAO.getOneCompany(this.getCompanyId());
    }


        public int getCompanyId() {
        return companyId;
    }
}
