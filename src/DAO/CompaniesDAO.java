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

    boolean isCompanyExists(String email,String password);

    int addCompany(Company company) throws ConnectException, CouponSystemException;

    void updateCompany(Company company) throws ConnectException, CouponSystemException;

    void deleteCompany(int companyId) throws ConnectException, CouponSystemException;

    ArrayList<Company> getAllCompanies() throws CouponSystemException;

    Company getOneCompany(int companyId) throws CouponSystemException, SQLException;

}
