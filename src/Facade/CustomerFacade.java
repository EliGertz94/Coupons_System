package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import Exceptions.CouponSystemException;

import java.sql.*;
import java.util.ArrayList;

public class CustomerFacade  extends ClientFacade {


    private int customerId;

    public Customer logIn(String email, String password) {

        Customer customer= customersDAO.isCustomerExists(email, password);
        this.customerId=customer.getId();
        return customer;
    }


    //o לא ניתן לרכוש את אותו הקופון יותר מפעם אחת.
    //o לא ניתן לרכוש את הקופון אם הכמות שלו היא 0.
    //o לא ניתן לרכוש את הקופון אם תאריך התפוגה שלו כבר הגיע.
    //o לאחר הרכישה יש להוריד את הכמות במלאי של הקופון ב-1.
    public void purchaseCoupon(int couponId) throws CouponSystemException {
        couponsDAO.addCouponPurchase(this.customerId,couponId);

    }


//    public ArrayList<Coupon> getCustomerCoupons(){
//
//    }

    public ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException, SQLException {

        String sql = "select * from coupons where coupons.id  in " +
                "(select COUPON_ID from CUSTOMERS_VS_COUPONS" +
                " where CUSTOMER_ID =" + this.customerId + ")";
        ArrayList<Coupon> coupons = new ArrayList<>();
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(resultSet.getInt(1));
                coupon.setCompanyId(resultSet.getInt(2));

                for (Category category : Category.values()) {
                    if (category.getCode() == resultSet.getInt(3)) {
                        coupon.setCategory(category);
                    }
                }
                coupon.setTitle(resultSet.getString(4));
                coupon.setDescription(resultSet.getString(5));
                Timestamp startTimestamp = new Timestamp(resultSet.getDate(6).getTime());
                coupon.setStartDate(startTimestamp.toLocalDateTime());
                Timestamp endTimestamp = new Timestamp(resultSet.getDate(7).getTime());
                coupon.setEndDate(endTimestamp.toLocalDateTime());

                coupon.setAmount(resultSet.getInt(8));
                coupon.setPrice(resultSet.getDouble(9));
                coupon.setImage(resultSet.getString(10));
                coupons.add(coupon);

            }

            resultSet.close();
            stm.close();
            return coupons;


        }}
    }