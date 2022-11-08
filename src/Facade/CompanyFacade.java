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
     * will instantiate  the companyId field
     */
    public boolean logIn(String email, String password) throws CouponSystemException {

        try {
            this.companyId=companiesDAO.companyByLogin(email,password).getId();
            return  companiesDAO.isCompanyExists(email,password);

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
        }else {
            System.out.println("not your company id");
        }

    }

    /**
     * addCoupon - will add a coupon record with unique title
     */
    public synchronized void updateCoupon(Coupon coupon) throws CouponSystemException {

        if(coupon.getCompanyId() != this.companyId) {
            throw new CouponSystemException("updateCoupon error at CompanyFacade");
        }
            couponsDAO.updateCoupon(coupon);
    }

    /**
     * deleteCoupon - delete coupon by couponId if belongs to company by companyId
     */
    public void deleteCoupon(int couponId) throws CouponSystemException {
        Coupon companyCoupon =  couponsDAO.getOneCoupon(couponId);

        if(companyCoupon.getCompanyId() != this.getCompanyId()){
           throw new CouponSystemException("deleteCoupon at CompanyFacade - no permission to delete coupon");
        }
        couponsDAO.deleteCoupon(couponId);
    }

    /**
     * getAllCompanyCoupons -
     */
    public  ArrayList<Coupon> getAllCompanyCoupons() throws CouponSystemException {
       return companiesDAO.getAllCompanyCoupons(this.getCompanyId());
    }

    public synchronized ArrayList<Coupon> getAllCompanyCoupons(Category category) throws CouponSystemException {
        return companiesDAO.getAllCompanyCoupons(category,this.getCompanyId());
    }


    public  ArrayList<Coupon> getAllCompanyCoupons(double maxPrice) throws CouponSystemException {
        return companiesDAO.getAllCompanyCoupons(maxPrice,this.getCompanyId());
    }

    /**
     * getCompanyDetails - returns company details including the coupons of the company
     */
    public  Company getCompanyDetails( ) throws CouponSystemException {

       Company company = companiesDAO.getOneCompany(this.getCompanyId());

        company.setCoupons(companiesDAO.getAllCompanyCoupons(this.getCompanyId()));

       return company;
    }

    /**
     * getCompanyId - get company id field
     */
    public int getCompanyId() {
        return companyId;
    }
}
