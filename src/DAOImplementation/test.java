package DAOImplementation;

import Beans.Company;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws CouponSystemException {


        CompaniesDAO companyimpl = new CompaniesDBDAO();
        Company company1 = new Company("eli","@1.com","12435435345@");


        ArrayList<Company> list =companyimpl.getAllCompanies();

        for (Company company:list
             ) {
            System.out.println(company);
        }

        try {
            System.out.println("chosen company" + companyimpl.getOneCompany(134));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //   System.out.println(company1);



    }

}
