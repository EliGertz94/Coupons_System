package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import Exceptions.CouponSystemException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;

public class CustomerFacade  extends ClientFacade {


    private int customerId;

    public synchronized Customer logIn(String email, String password) {

        Customer customer= null;
        try {
            customer = customersDAO.isCustomerExists(email, password);
        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerId =customer.getId();
        return customer;

    }


    //o לא ניתן לרכוש את אותו הקופון יותר מפעם אחת.V
    //o לא ניתן לרכוש את הקופון אם הכמות שלו היא 0.V
        //o לא ניתן לרכוש את הקופון אם תאריך התפוגה שלו כבר הגיע.
        //o לאחר הרכישה יש להוריד את הכמות במלאי של הקופון ב-1.
        public synchronized void purchaseCoupon(int couponId) throws CouponSystemException {
            if(!couponsDAO.doesCustomerPurchaseExist(this.customerId,couponId)){

                if(couponsDAO.getOneCoupon(couponId).getAmount()>0
                        && couponsDAO.getOneCoupon(couponId).getEndDate().isBefore(LocalDateTime.now())) {
                    couponsDAO.addCouponPurchase(this.customerId, couponId);
                }
            }else{
                System.out.println("already purchest this coupon");
            }

    }


    public synchronized ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException, SQLException {

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

    public synchronized ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException, SQLException {


        String sql = "select * from coupons where coupons.id  in " +
                "(select COUPON_ID from CUSTOMERS_VS_COUPONS" +
                " where CUSTOMER_ID =" + this.customerId + ")  AND coupons.PRICE <= "+maxPrice;
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


        }

    }

    public synchronized Customer getCustomerDetails(){
        try {
            return customersDAO.getOneCustomer(this.customerId);
        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        }

    }

    }