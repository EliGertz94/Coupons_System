package DAOImplementation;

import Beans.Coupon;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CouponsDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {
    @Override
    public int addCoupon(Coupon coupon) throws CouponSystemException {




        String SQL = " insert into coupons(\n" +
                "                COMPANY_ID\n" +
                "                ,CATEGORY_ID\n" +
                "                ,TITLE\n" +
                "                ,DESCRIPTION\n" +
                "                ,START_DATE\n" +
                "                ,END_DATE\n" +
                "                ,AMOUNT\n" +
                "                ,PRICE\n" +
                "                ,IMAGE) values(?,?,?,?,?,?,?,?,?)";
        try{

            Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, coupon.getCompanyId());
            pstmt.setInt(2, coupon.getCategory().getCode());
            pstmt.setString(3, coupon.getTitle());
            pstmt.setString(4, coupon.getDescription());
            pstmt.setDate(5, Date.valueOf(coupon.getStartDate().toLocalDate()));
            pstmt.setDate(6, Date.valueOf(coupon.getStartDate().toLocalDate()));
            pstmt.setInt(7, coupon.getAmount());
            pstmt.setDouble(8, coupon.getPrice());
            pstmt.setString(9, coupon.getImage());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            int id= resultSet.getInt(1);
            System.out.println("company with id number "+ id +  " was created");

            ConnectionPool.getInstance().restoreConnection(con);
         return id;

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("add company error");

        }

    }

    @Override
    public void updateCoupon(Coupon coupon) {

    }

    @Override
    public void deleteCoupon(int couponId) {

    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        return null;
    }

    @Override
    public Coupon getOneCoupon(int couponId) {
        return null;
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) {

    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) {

    }
}
