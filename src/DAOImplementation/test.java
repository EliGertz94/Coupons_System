package DAOImplementation;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import Exceptions.CouponSystemException;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import jdk.swing.interop.SwingInterOpUtils;

import java.net.ConnectException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws CouponSystemException, ConnectException {

        ConnectionPool connectionPool= ConnectionPool.getInstance();
        CouponsDBDAO couponsDAO = new CouponsDBDAO();

        String str = "2022-12-30 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        Coupon coupon = new Coupon();
        coupon.setAmount(100);
        coupon.setDescription("eating ex");
        coupon.setImage("eatingman.png");
        coupon.setCategory(Category.Food);
        coupon.setStartDate(LocalDateTime.now());
        coupon.setEndDate(LocalDateTime.now());
        coupon.setPrice(120);
        coupon.setEndDate(dateTime);
        coupon.setCompanyId(786876);
        coupon.setTitle("foodies ");
        //
//        coupon.setEndDate(LocalDateTime.of(2022, 11, 13, 15, 56));
      //  System.out.println(couponsDAO.doesCouponExists(4));
        CouponsDBDAO couponsDAO1 = new CouponsDBDAO();
        CompanyFacade companyFacade = new CompanyFacade();
       // companyFacade.logIn("maam@gmail.com","word");
        companyFacade.addCoupon(coupon);
        CustomerFacade customerFacade = new CustomerFacade();
        customerFacade.logIn("email1@gmail.com","password");
        customerFacade.purchaseCoupon(14);
        System.out.println(companyFacade.getAllCompanyCoupons(2));
//        CompanyFacade companyFacade = new CompanyFacade();
//        CompanyFacade companyFacade1 = new CompanyFacade();
//        Company company = companyFacade1.logIn("mmmm@gmail.com","alla199");
//        System.out.println(company);
//        for (Coupon coupon1:companyFacade1.getAllCompanyCoupons())
//        {
//            System.out.println(coupon1);
//        }
//
//
////        companyFacade.addCoupon(coupon);
////       ArrayList<Coupon> coupons =  companyFacade.getAllCompanyCoupons();
////
////       for(Coupon coupon1 : coupons){
////           System.out.println(coupon1);
//       }
//        //System.out.println(couponsDAO.uniqueTitleByCompany(3,coupon.getTitle()));
//        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
//        System.out.println(companiesDBDAO.checkCompany(3));

     //    couponsDAO.deleteCouponPurchase(1,4);
      //  System.out.println(couponsDAO.getOneCoupon(11));
//    for(Coupon coupon1 : list ){
//        System.out.println(coupon1);
//    }



        //   System.out.println(company1);



    }

}
