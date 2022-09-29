package Facade;

import Beans.Company;
import Beans.Customer;
import DAOImplementation.CompaniesDBDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;

public class test {

    public static void main(String[] args) throws CouponSystemException {



        AdminFacade adminFacade = new AdminFacade();
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        Company company = new Company("amm","maam@gmail.com","word");
        Customer customer = new Customer("Avi","gertz",
                "Avil@gmail.com","123");
        customer.setId(2);
        adminFacade.deleteCustomer(customer.getId());




    }
}
