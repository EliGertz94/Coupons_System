package DAO;

import Beans.Company;
import Beans.Customer;
import Exceptions.CouponSystemException;

import java.util.ArrayList;

public interface CustomersDAO {

    /**
     * A Customers DAO (data access object) for CRUD
     *
     *
     * */

    boolean isCustomerExists(String email,String password);

    int addCustomer(Customer customer) throws CouponSystemException;

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerId);

    ArrayList<Customer> getAllCustomers();

    Company getOneCustomer(int companyId);

}
