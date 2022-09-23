package DAOImplementation;

import Beans.Company;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import DAO.CustomersDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws CouponSystemException {


        CustomersDAO customersDAO = new CustomersDBDAO();
        Company company1 = new Company("eli","@1.com","12435435345@");

        Customer customer = new Customer("Eli the first ","Gertzman","eli@gmail.com","123445E");


        customer.setId(7);
        System.out.println(customersDAO.getOneCustomer(1));




        //   System.out.println(company1);



    }

}
