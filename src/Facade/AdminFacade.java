package Facade;

import Beans.Company;
import Beans.Customer;

import Exceptions.CouponSystemException;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    /**
     * logIn - return true/false if login correct for Admin
     */
    public boolean logIn(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    /**
     * addCompany -  adding a company if the new company has a unique email and name exist
     */
    public void addCompany(Company company) throws CouponSystemException {
        try{
            if (!companiesDAO.getCompanyByName(company.getName()) &&
                    !companiesDAO.getCompanyByEmail(company.getEmail())) {

                companiesDAO.addCompany(company);
            } else {

                throw new CouponSystemException("addCompany- email or name exist already");
            }
        }catch (CouponSystemException e){
            throw new CouponSystemException("addCompany at Admin Error",e);
        }
    }

    /**
     * updateCompany -   update company if email is unique
     * updating if the name is the same
     */
    public  void updateCompany(Company company) throws CouponSystemException {
        try{
                if(!company.getName().equals(companiesDAO.getOneCompany(company.getId()).getName())){
                    throw new CouponSystemException("Admin updateCompany -  can't change the name of the company");
                }
            companiesDAO.updateCompany(company);

        }catch(CouponSystemException e ){
            throw new CouponSystemException("updateCompany at AdminFacade",e);
        }

    }

    /**
     * deleteCompany - delete Company record using the DAO
     */
    public  void deleteCompany(int comapnyId) throws CouponSystemException {
        try{
            companiesDAO.deleteCompany(comapnyId);
        }catch (CouponSystemException e){
            throw  new CouponSystemException(" deleteCompany at Admin ",e);
        }
    }

    /**
     * deleteCompany - delete Company record using the DAO
     */
    public  ArrayList<Company> getAllCompanies() throws CouponSystemException {
        try{
            return companiesDAO.getAllCompanies();
        }catch (CouponSystemException e){
            throw new CouponSystemException("getAllCompanies at AdminFacade",e);
        }
    }

    /**
     * getOneCompany - returns company object according to company id
     */
    public  Company getOneCompany(int companyId) throws CouponSystemException {
        try {
            return companiesDAO.getOneCompany(companyId);
        } catch (CouponSystemException e) {
           throw new CouponSystemException("getOneCompany at Admin ",e);
        }

    }

    /**
     * addCustomer -  adding a customer record to customers record
     */
    public  void addCustomer (Customer customer) throws CouponSystemException {
        try{
            if (!customersDAO.getCustomerByEmail(customer.getEmail())) {

                customersDAO.addCustomer(customer);

            } else {
                throw new CouponSystemException("this email exist already");
            }
        }catch (CouponSystemException e){
            throw new CouponSystemException("addCustomer at AdminFacade Error",e);
        }
    }

    /**
     *update a new customer - you can't update a customer with a new id
     * because of DAO wouldn't accept new id
     */
    public  void updateCustomer (Customer customer) throws CouponSystemException {
        try{
                customersDAO.updateCustomer(customer);
        }catch (CouponSystemException e ){
            throw new CouponSystemException("updateCustomer at AdminFacade",e);
        }
    }

    /**
     * deleteCustomer - delete customer by id
     * will work with cascade to delete the purchases -
     * (*) on delete company without I did it without cascade!
     */
    public  void deleteCustomer(int customerId) throws CouponSystemException {
        try{
            customersDAO.deleteCustomer(customerId);
        }catch(CouponSystemException e){
            throw new CouponSystemException("deleteCustomer at AdminFacade",e);
        }
    }

    /**
     * getAllCustomers - returns all the customers
     */
    public  ArrayList<Customer> getAllCustomers() throws CouponSystemException {
        try{
            return customersDAO.getAllCustomers();
        }catch (CouponSystemException e){
            throw new CouponSystemException("getAllCustomers at AdminFacade",e);
        }
    }

    /**
     * getOneCustomer - returns customer according to id
     */
    public  Customer getOneCustomer(int customerId) throws CouponSystemException {
        try{
            return customersDAO.getOneCustomer(customerId);
        }catch(CouponSystemException e){
            throw new CouponSystemException("getOneCustomer at AdminFacade",e);
        }
    }



}
