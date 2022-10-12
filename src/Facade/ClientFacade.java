package Facade;

import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import DAOImplementation.CustomersDBDAO;

public abstract class ClientFacade {

    //ConnectionPool connectionPool = ConnectionPool.getInstance();

    protected    CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected    CouponsDAO couponsDAO = new CouponsDBDAO();
    protected    CustomersDAO customersDAO=  new CustomersDBDAO();

    /// initiat with interface and put everything on interface

    //public abstract boolean logIn(String email, String password);
}
