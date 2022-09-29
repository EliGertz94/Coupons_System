package Facade;

import Beans.Company;
import DAOImplementation.CompaniesDBDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.SQLException;

public class test {

    public static void main(String[] args) {


        try{
        AdminFacade adminFacade = new AdminFacade();
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        Company company = new Company("amm","maam@gmail.com","word");
        company.setId(companiesDBDAO.getOneCompany(4).getId());
            System.out.println(adminFacade.getAllCompanies());

        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        }


    }
}
