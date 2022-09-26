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

import java.net.ConnectException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws CouponSystemException {


        CouponsDAO couponsDAO = new CouponsDBDAO();

        String str = "2022-10-30 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        Coupon coupon = new Coupon();
        coupon.setAmount(100);
        coupon.setDescription("eating ex");
        coupon.setImage("eatingman.png");
        coupon.setCategory(Category.Food);
        coupon.setStartDate(LocalDateTime.now());
        coupon.setEndDate(dateTime);
        coupon.setCompanyId(120);
        coupon.setTitle("eating");
        //
//        coupon.setEndDate(LocalDateTime.of(2022, 11, 13, 15, 56));
        coupon.setId(8);
       // couponsDAO.updateCoupon(coupon);
        ArrayList<Coupon> list = couponsDAO.getAllCoupons();
        System.out.println(couponsDAO.getOneCoupon(11));
//    for(Coupon coupon1 : list ){
//        System.out.println(coupon1);
//    }



        //   System.out.println(company1);



    }

}
