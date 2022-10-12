package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ClientLogIn.ClientType;
import ClientLogIn.LoginManager;
import DAO.CouponsDAO;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import Exceptions.CouponSystemException;
import Job.CouponExpirationDailyJob;

import java.net.ConnectException;
import java.nio.charset.CoderMalfunctionError;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class test {

    public static void main(String[] args) throws CouponSystemException, ConnectException, SQLException {


//        LoginManager loginManager = LoginManager.getInstance();
//        System.out.println(loginManager.logIn("admin@admin.com","admin", ClientType.Administrator));

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String str = "2022-12-30 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);
        CompanyFacade companyFacade = new CompanyFacade();
        companyFacade.logIn("mmmm@gmail.com","alla199");
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

        CouponsDAO couponsDAO = new CouponsDBDAO();
        couponsDAO.updateCoupon(coupon);

    }
}
