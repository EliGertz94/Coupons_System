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
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws CouponSystemException {


        CouponsDAO customersDAO = new CouponsDBDAO();
        Coupon coupon = new Coupon();
        coupon.setAmount(100);
        coupon.setDescription("eating ex");
        coupon.setImage("eatingman.png");
        coupon.setCategory(Category.Food);
        coupon.setStartDate(LocalDateTime.now());
        coupon.setCompanyId(120);
        coupon.setTitle("eating");
        //
        coupon.setEndDate(LocalDateTime.of(2022, 11, 13, 15, 56));

        coupon.setId(customersDAO.addCoupon(coupon));




        //   System.out.println(company1);



    }

}
