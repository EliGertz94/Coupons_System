package Facade;

import Beans.Company;
import Beans.Customer;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DAOImplementation.CompaniesDBDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    /**
     * logIn - return true/false if login correct for Admin
     */
    public boolean logIn(String email, String password) {
       if(email.equals("admin@admin.com") && password.equals("admin")){
           return true;
       }
       return false;
    }

    public synchronized void addCompany(Company company) throws CouponSystemException {
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


    public synchronized void updateCompany(Company company) throws CouponSystemException {

        try{


            if(!company.getEmail().equals(companiesDAO.getOneCompany(company.getId()).getEmail())
            && !companiesDAO.getCompanyByEmail(company.getEmail())
            ){

                if(company.getName().equals(companiesDAO.getOneCompany(company.getId()).getName())){
                    companiesDAO.updateCompany(company);
                    System.out.println(company.getName() + " was updated");
                }else {
                    System.out.println("you can't changhe the name of the company");
                }

            } else {
                System.out.println("company not updated,can't change the name of the company, try again.. ");
            }

          //  companiesDAO.updateCompany(company);
        }catch(CouponSystemException e ){
            throw new CouponSystemException("updateCompany at AdminFacade",e);
        }

    }


    public synchronized void deleteCompany(Company company) throws CouponSystemException {
        try{
            companiesDAO.deleteCompany(company.getId());
        }catch (CouponSystemException e){
            throw  new CouponSystemException(" deleteCompany at Admin ",e);
        }
    }

    public synchronized ArrayList<Company> getAllCompanies() throws CouponSystemException {
        try{
            return companiesDAO.getAllCompanies();
        }catch (CouponSystemException e){
            throw new CouponSystemException("getAllCompanies at AdminFacade",e);
        }
    }

    public synchronized Company getOneCompany(int companyId) throws CouponSystemException {
        try {
            return companiesDAO.getOneCompany(companyId);
        } catch (CouponSystemException e) {
           throw new CouponSystemException("getOneCompany at Admin ",e);
        }

    }

    public synchronized void addCustomer (Customer customer) throws CouponSystemException {
        try{
            if (!customersDAO.getCustomerByEmail(customer.getEmail())) {
                customersDAO.addCustomer(customer);
                System.out.println(customer.getFirstName() + "  " + customer.getLastName()
                        + " was added");
            } else {
                System.out.println("this email exit already");
            }
        }catch (CouponSystemException e){
            throw new CouponSystemException("addCustomer at AdminFacade",e);
        }
    }

    // לא ניתן לעדכן את קוד הלקוח.
    public synchronized void updateCustomer (Customer customer) throws CouponSystemException {
        try{
            if (customer.getEmail().equals(customersDAO.getOneCustomer(customer.getId()))) {
                customersDAO.updateCustomer(customer);
                System.out.println(customer.getFirstName() + " was updated");
            } else if (!customersDAO.getCustomerByEmail(customer.getEmail())) {
                customersDAO.updateCustomer(customer);
                System.out.println(customer.getFirstName() + " was updated");

            } else {
                System.out.println("you are trying to update an email that exist already");
            }
        }catch (CouponSystemException e ){
            throw new CouponSystemException("updateCustomer at AdminFacade",e);
        }
    }


    public synchronized void deleteCustomer(int customerId) throws CouponSystemException {
        try{
            customersDAO.deleteCustomer(customerId);
        }catch(CouponSystemException e){
            throw new CouponSystemException("deleteCustomer at AdminFacade",e);
        }
    }

    public synchronized ArrayList<Customer> getAllCustomers() throws CouponSystemException {
        try{
            return customersDAO.getAllCustomers();
        }catch (CouponSystemException e){
            throw new CouponSystemException("getAllCustomers at AdminFacade",e);
        }
    }

    public synchronized Customer getOneCustomer(int customerId) throws CouponSystemException {
        try{
            return customersDAO.getOneCustomer(customerId);
        }catch(CouponSystemException e){
            throw new CouponSystemException("getOneCustomer at AdminFacade",e);
        }
    }



}
