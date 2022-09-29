package Facade;

import Beans.Company;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DAOImplementation.CompaniesDBDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.util.ArrayList;

public class AdminFacade extends  ClientFacade {

    @Override
    public boolean logIn(String email, String password) {
       if(email.equals("admin@admin.com") && password.equals("admin")){
           return true;
       }
       return false;
    }

    public void addCompany(Company company) throws CouponSystemException {
        if(!companiesDAO.getCompanyByName(company.getName())&&
        !companiesDAO.getCompanyByEmail(company.getEmail()) )
        {
            companiesDAO.addCompany(company);
        }else {
            System.out.println("email or password exist already");
       }
    }


    public void updateCompany(Company company) throws CouponSystemException {

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


    public void deleteCompany(Company company) throws CouponSystemException {
        companiesDAO.deleteCompany(company.getId());
    }

    public ArrayList<Company> getAllCompanies() throws CouponSystemException {
       return companiesDAO.getAllCompanies();
    }

}
