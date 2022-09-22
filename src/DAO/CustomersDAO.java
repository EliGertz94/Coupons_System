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

    boolean isCustomerExists(String email,String password);

    int addCustomer(Customer customer) throws CouponSystemException;

    void updateCustomer(Customer customer) throws CouponSystemException;

    void deleteCustomer(int customerId);

    ArrayList<Customer> getAllCustomers();

    Company getOneCustomer(int companyId);

}
