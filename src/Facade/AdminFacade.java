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
     * addCompany -  adding a company if
     */
    public  void addCompany(Company company) throws CouponSystemException {
        try{
            if (!companiesDAO.getCompanyByName(company.getName()) &&
                    !companiesDAO.getCompanyByEmail(company.getEmail())) {
                companiesDAO.addCompany(company);
            } else {
                System.out.println("email or password exist already");
            }
        }catch (CouponSystemException e){
            throw new CouponSystemException("addCompany at Admin ",e);
        }
    }

    /**
     * updateCompany -   update company if email is unique
     * updating if the name is the same
     */
    public  void updateCompany(Company company) throws CouponSystemException {

        try{

            if(!company.getEmail().equals(companiesDAO.getOneCompany(company.getId()).getEmail())
            && !companiesDAO.getCompanyByEmail(company.getEmail())
            ){

                if(company.getName().equals(companiesDAO.getOneCompany(company.getId()).getName())){
                    companiesDAO.updateCompany(company);
                    System.out.println(company.getName() + " was updated");
                }else {
                    System.out.println("you can't change the name of the company");
                }

            } else {
                System.out.println("company not updated,can't change the name of the company, try again.. ");
            }

        }catch(CouponSystemException e ){
            throw new CouponSystemException("updateCompany at AdminFacade",e);
        }

    }

    /**
     * deleteCompany - delete Company record using the DAO
     */
    public  void deleteCompany(Company company) throws CouponSystemException {
        try{
            companiesDAO.deleteCompany(company.getId());
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
                System.out.println(customer.getFirstName() + "  " + customer.getLastName()
                        + " was added");
            } else {
                System.out.println("this email exist already");
            }
        }catch (CouponSystemException e){
            throw new CouponSystemException("addCustomer at AdminFacade",e);
        }
    }

    /**
     * updateCustomer - updating customer and checking for duplicate email input
     * if email is found already it has to be the current object with the current id
     */
    public  void updateCustomer (Customer customer) throws CouponSystemException {
        try{
            if (!customersDAO.getCustomerByEmail(customer.getEmail())) {
                customersDAO.updateCustomer(customer);
                System.out.println(customer.getFirstName() + " was updated");
            } else if (customersDAO.getCustomerByEmail(customer.getEmail())
                    && customer.getEmail().equals(customersDAO.getOneCustomer(customer.getId()).getEmail())) {
                customersDAO.updateCustomer(customer);
                System.out.println(customer.getFirstName() + " was updated");

            } else {
                System.out.println("you are trying to update an email that exist already");
            }
        }catch (CouponSystemException e ){
            throw new CouponSystemException("updateCustomer at AdminFacade",e);
        }
    }

    /**
     * deleteCustomer - delete customer by id
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
