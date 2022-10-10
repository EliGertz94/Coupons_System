package Facade;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAOImplementation.CompaniesDBDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.nio.charset.CoderMalfunctionError;
import java.sql.SQLException;

public class test {

    public static void main(String[] args) throws CouponSystemException, ConnectException {


//
//        AdminFacade adminFacade = new AdminFacade();
//        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
//        Company company = new Company("amm","maam@gmail.com","word");
//        Customer customer = new Customer("Avi","gertz",
//                "Avil@gmail.com","123");
//       // customer.setId(2);
//        adminFacade.addCompany(company);

        CustomerFacade customerFacade = new CustomerFacade();
        customerFacade.logIn("email1@gmail.com","password");
        customerFacade.purchaseCoupon(10);
        //System.out.println(customer);

        System.out.println(customerFacade.getCustomerDetails()+" get customer details");
        try {
           for(Coupon coupon:customerFacade.getCustomerCoupons()) {
               System.out.println(coupon);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
