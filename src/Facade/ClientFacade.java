package Facade;

import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import DAOImplementation.CustomersDBDAO;
import Exceptions.CouponSystemException;

/**
 * ClientFacade -abstract class containing all the DAO Class instances
 */
public abstract class ClientFacade {

    protected    CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected    CouponsDAO couponsDAO = new CouponsDBDAO();
    protected    CustomersDAO customersDAO=  new CustomersDBDAO();

    /**
     * logIn -abstract method returns true/false if login is correct
     */
    public abstract boolean logIn(String email, String password) throws CouponSystemException;
}
