package Facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import DAOImplementation.CustomersDBDAO;

public abstract class ClientFacade {

     public   CompaniesDBDAO companiesDAO = new CompaniesDBDAO();
     public    CouponsDAO couponsDAO = new CouponsDBDAO();
     public    CustomersDAO customersDAO=  new CustomersDBDAO();


    public abstract boolean logIn(String email, String password);
}
