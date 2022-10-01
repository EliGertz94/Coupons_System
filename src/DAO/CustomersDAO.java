package DAO;

import Beans.Company;
import Beans.Customer;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.util.ArrayList;

public interface CustomersDAO {

    /**
     * A Customers DAO (data access object) for CRUD
     *
     *
     * */

    Customer isCustomerExists(String email,String password);

    int addCustomer(Customer customer) throws CouponSystemException;

    void updateCustomer(Customer customer) throws CouponSystemException;

    void deleteCustomer(int customerId) throws CouponSystemException;

    ArrayList<Customer> getAllCustomers() throws CouponSystemException;

    Customer getOneCustomer(int customerId) throws CouponSystemException;

    boolean getCustomerByEmail(String companyEmail) throws CouponSystemException;

}
