package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ClientLogIn.ClientType;
import ClientLogIn.LoginManager;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CouponsDAO;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import Exceptions.CouponSystemException;
import Job.CouponExpirationDailyJob;

import java.net.ConnectException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class test {

    public static void main(String[] args) throws CouponSystemException, ConnectException, SQLException {


//        CompanyFacade companyFacade = new CompanyFacade();
//        System.out.println(companyFacade.logIn("mmmm@gmail.com","alla199"));
//        CompanyFacade companyFacade1 = new CompanyFacade();
//        System.out.println(companyFacade1.logIn("mmmm@gmail.com","alla199"));
        LoginManager loginManager = LoginManager.getInstance();
        CompanyFacade companyFacade = (CompanyFacade)(loginManager.logIn("mmmm@gmail.com","alla199", ClientType.Company));
        System.out.println("comapny facade 1 "+ companyFacade);
        System.out.println("admin facade 2 "+loginManager.logIn("admin@admin.com","admin", ClientType.Administrator));
        System.out.println("comapny facade 3 "+loginManager.logIn("maam@gmail.com","word", ClientType.Company));
        System.out.println("customer facade 4 "+loginManager.logIn("email1@gmail.com","password", ClientType.Customer));

        System.out.println("customer facade 5 "+loginManager.logIn("email1@gmail.com","password", ClientType.Customer));
        System.out.println("company facade 6 "+loginManager.logIn("maam@gmail.com","word", ClientType.Company));

//        LoginManager loginManager2 = LoginManager.getInstance();
//        System.out.println(loginManager2.logIn("mmmm@gmail.com","alla199", ClientType.Company));
//
//        CompanyFacade companyFacade2 = new CompanyFacade();
//        System.out.println(companyFacade2.logIn("mmmm@gmail.com","alla199"));
//        CompanyFacade companyFacade3 = new CompanyFacade();
//        System.out.println(companyFacade3.logIn("mmmm@gmail.com","alla199"));


       // ConnectionPool.getInstance().closeAllConnections();



        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String str = "2022-12-30 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//        System.out.println(dateTime);

        Coupon coupon = new Coupon();
        coupon.setAmount(100);
        coupon.setDescription("eating ex");
        coupon.setImage("eatingman.png");
        coupon.setCategory(Category.Food);
        coupon.setStartDate(LocalDateTime.now());
        coupon.setPrice(120);
        coupon.setEndDate(dateTime);
        coupon.setCompanyId(3);
        coupon.setTitle("bbb");
        coupon.setId(26);
        //companyFacade.addCoupon(coupon);
       // CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
      //  couponExpirationDailyJob.run();

//        CouponsDAO couponsDAO = new CouponsDBDAO();
//        couponsDAO.updateCoupon(coupon);

    }
}
