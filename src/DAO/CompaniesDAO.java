package DAO;

import Beans.Company;
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

    Company isCompanyExists(String email,String password);

    int addCompany(Company company) throws  CouponSystemException;

    void updateCompany(Company company) throws  CouponSystemException;

    void deleteCompany(int companyId) throws  CouponSystemException;

    void deleteFromCoupons(int companyId) throws CouponSystemException;
    void deleteFromCVC(int companyId) throws CouponSystemException;
    boolean getCompanyByName(String companyName) throws CouponSystemException;

    ArrayList<Company> getAllCompanies() throws CouponSystemException;

    Company getOneCompany(int companyId) throws CouponSystemException;

    boolean getCompanyByEmail(String companyEmail) throws CouponSystemException;

}
