package DAO;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {

    /**
     * A company DAO (data access object) for CRUD
     *
     *
     * */

    boolean isCompanyExists(String email,String password) throws CouponSystemException;
    Company companyByLogin(String email, String password) throws CouponSystemException;

    int addCompany(Company company) throws  CouponSystemException;

    void updateCompany(Company company) throws  CouponSystemException;

    void deleteCompany(int companyId) throws  CouponSystemException;

    void deleteFromCoupons(int companyId) throws CouponSystemException;
    void deleteFromCVC(int companyId) throws CouponSystemException;
    boolean getCompanyByName(String companyName) throws CouponSystemException;

    ArrayList<Company> getAllCompanies() throws CouponSystemException;

    Company getOneCompany(int companyId) throws CouponSystemException;

    boolean getCompanyByEmail(String companyEmail) throws CouponSystemException;

    ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws CouponSystemException;

     ArrayList<Coupon> getAllCompanyCoupons(Category category , int companyId) throws CouponSystemException;
    ArrayList<Coupon> getAllCompanyCoupons(double maxPrice,int couponId) throws CouponSystemException;

}
