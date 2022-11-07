package DAO;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {

    /**
     * A Customers DAO (data access object) for CRUD
     *
     *
     * */

    boolean isCustomerExists(String email,String password) throws CouponSystemException, SQLException;

    Customer customerByLogIn(String email, String password) throws CouponSystemException;

    int addCustomer(Customer customer) throws CouponSystemException;

    void updateCustomer(Customer customer) throws CouponSystemException;

    void deleteCustomer(int customerId) throws CouponSystemException;

    ArrayList<Customer> getAllCustomers() throws CouponSystemException;

    Customer getOneCustomer(int customerId) throws CouponSystemException;

    boolean getCustomerByEmail(String companyEmail) throws CouponSystemException;
    ArrayList<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException;

    ArrayList<Coupon> getCustomerCoupons(double maxPrice,int customerId) throws CouponSystemException;
    ArrayList<Coupon> getCustomerCoupons(Category couponCategory, int customerId) throws CouponSystemException;


}
