package Facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import DAOImplementation.CustomersDBDAO;

public abstract class ClientFacade {

    protected    CompaniesDBDAO companiesDAO = new CompaniesDBDAO();
    protected    CouponsDBDAO couponsDAO = new CouponsDBDAO();
    protected    CustomersDBDAO customersDAO=  new CustomersDBDAO();

    /// initiat with interface and put everything on interface

    public abstract boolean logIn(String email, String password);
}
