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

    public boolean logIn(String email, String password) {
       if(email.equals("admin@admin.com") && password.equals("admin")){
           return true;
       }
       return false;
    }

    public synchronized void addCompany(Company company) throws CouponSystemException {
        if(!companiesDAO.getCompanyByName(company.getName())&&
        !companiesDAO.getCompanyByEmail(company.getEmail()) )
        {
            companiesDAO.addCompany(company);
        }else {
            System.out.println("email or password exist already");
       }
    }


    public synchronized void updateCompany(Company company) throws CouponSystemException, SQLException {

        if(company.getEmail().equals(companiesDAO.getOneCompany(company.getId()).getEmail()))
        {
            companiesDAO.updateCompany(company);
            System.out.println(company.getName() + " was updated");
        }else if(!companiesDAO.getCompanyByEmail(company.getEmail()) )
        {
            companiesDAO.updateCompany(company);
            System.out.println(company.getName() + " was updated");

        }
        else {
            System.out.println("you are trying to update an email that exist already");
        }
    }


    public  synchronized void deleteCompany(Company company) throws CouponSystemException {
        companiesDAO.deleteCompany(company.getId());
    }

    public synchronized ArrayList<Company> getAllCompanies() throws CouponSystemException {
       return companiesDAO.getAllCompanies();
    }

    //how to only send massage to user
    public synchronized Company getOneCompany(int companyId) {
        try {
            return companiesDAO.getOneCompany(companyId);
        } catch (CouponSystemException e) {
            return null;
        }

    }

    public synchronized void addCustomer (Customer customer) throws CouponSystemException {
        if(!customersDAO.getCustomerByEmail(customer.getEmail())){
            customersDAO.addCustomer(customer);
            System.out.println(customer.getFirstName()+ "  " +customer.getLastName()
            + " was added");
        }else {
            System.out.println("this email exit already");
        }
    }

    // לא ניתן לעדכן את קוד הלקוח.
    public synchronized void updateCustomer (Customer customer) throws CouponSystemException {
        if(customer.getEmail().equals(customersDAO.getOneCustomer(customer.getId())))
        {
            customersDAO.updateCustomer(customer);
            System.out.println(customer.getFirstName() + " was updated");
        }else if(!customersDAO.getCustomerByEmail(customer.getEmail()) )
        {
            customersDAO.updateCustomer(customer);
            System.out.println(customer.getFirstName() + " was updated");

        }
        else {
            System.out.println("you are trying to update an email that exist already");
        }
    }

    //מחיקת לקוח קיים.
    //o יש למחוק בנוסף גם את היסטוריית רכישת הקופונים של הלקוח.

    public synchronized void deleteCustomer(int customerId) throws CouponSystemException {
        customersDAO.deleteCustomer(customerId);
    }

    public synchronized ArrayList<Customer> getAllCustomers() throws CouponSystemException {
        return customersDAO.getAllCustomers();
    }

    public synchronized Customer getOneCustomer(int customerId) throws CouponSystemException {
        return  customersDAO.getOneCustomer(customerId);
    }



}
