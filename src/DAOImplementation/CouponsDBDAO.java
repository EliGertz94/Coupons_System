package DAOImplementation;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CouponsDAO;
import Exceptions.CouponSystemException;
import java.sql.*;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {


    /**
     * doesCouponExists - returns true/false if a coupon with an given id exist
     */
    @Override
    public synchronized boolean doesCouponExists(int couponId) throws CouponSystemException {
        Connection  connection = ConnectionPool.getInstance().getConnection();
        try {


            PreparedStatement ps =
            connection.prepareStatement("SELECT id FROM coupons WHERE id = ?");

            ps.setInt(1, couponId);
            ResultSet result = ps.executeQuery();

            return  result.next();

        } catch (SQLException e) {
            throw new CouponSystemException("doesCouponExists error at CouponsDBDAO", e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(connection);

        }
    }

    /**
     * addCoupon -add coupon record to coupons table
     * returns the id of the coupon that was added
     */
    @Override
    public synchronized int addCoupon(Coupon coupon) throws CouponSystemException {


        int id=0;

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
        Connection con = ConnectionPool.getInstance().getConnection();
        try{

            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, coupon.getCompanyId());
            pstmt.setInt(2, coupon.getCategory().getCode());
            pstmt.setString(3, coupon.getTitle());
            pstmt.setString(4, coupon.getDescription());
            pstmt.setDate(5, Date.valueOf(coupon.getStartDate().toLocalDate()));
            pstmt.setDate(6, Date.valueOf(coupon.getEndDate().toLocalDate()));
            pstmt.setInt(7, coupon.getAmount());
            pstmt.setDouble(8, coupon.getPrice());
            pstmt.setString(9, coupon.getImage());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            resultSet.next();

            id = resultSet.getInt(1);

         return id;

        } catch (SQLException e) {
            throw new CouponSystemException("addCoupon error at CouponsDBDAO");

        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }

    }

    /**
     * updateCoupon - returns true/false if a coupon with an given id exist
     */
    @Override
    public synchronized void updateCoupon(Coupon coupon) throws CouponSystemException {
        String sql = "UPDATE coupons SET " +
                "COMPANY_ID = ?,"+
                "CATEGORY_ID = ?,"+
                "TITLE = ?," +
                "DESCRIPTION = ?," +
                "START_DATE =?, " +
                "END_DATE =?, " +
                "AMOUNT =?," +
                " PRICE = ?," +
                "IMAGE = ?" +
                " WHERE id = ?";
        Connection con = ConnectionPool.getInstance().getConnection();
        try
        ( PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1,coupon.getCompanyId());
            ps.setInt(2,coupon.getCategory().getCode());
            ps.setString(3,coupon.getTitle());
            ps.setString(4,coupon.getDescription());
            ps.setDate(5,Date.valueOf(coupon.getStartDate().toLocalDate()));
            ps.setDate(6,Date.valueOf(coupon.getEndDate().toLocalDate()));
            ps.setInt(7,coupon.getAmount());
            ps.setDouble(8,coupon.getPrice());
            ps.setString(9,coupon.getImage());
            ps.setInt(10,coupon.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CouponSystemException("updateCoupon error at CouponDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }

    }

    /**
     * deleteCoupon - delete coupon record by id
     */
    @Override
    public synchronized void deleteCoupon(int couponId) throws CouponSystemException {
        String sql = "delete from coupons where id  = " + couponId;
        Connection con = ConnectionPool.getInstance().getConnection();
        try {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);

            System.out.println("deleteCoupon amount of rows effected "+ rawCount);

        } catch (SQLException e) {
            throw new CouponSystemException("deleteCoupon error at CouponDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * getAllCoupons -  returns arraylist of type Coupon object
     * of all the coupons at the coupons table
     */
    @Override
    public synchronized ArrayList<Coupon> getAllCoupons() throws CouponSystemException {

        String sql = "select * from coupons";
        ArrayList<Coupon> coupons  = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            while (resultSet.next()){
                Coupon coupon = new Coupon();
                coupon.setId(resultSet.getInt(1));
                coupon.setCompanyId(resultSet.getInt(2));
                for (Category category : Category.values()) {
                   if(category.getCode() == resultSet.getInt(3)){
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

        }catch (SQLException e) {
            throw new CouponSystemException("getAllCoupons error at CouponDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }

    /**
     * getOneCoupon - returns coupon object by coupon id
     */
    @Override
    public synchronized Coupon getOneCoupon(int couponId) throws CouponSystemException {

        String sql = "select * from coupons where id = "+ couponId;
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            resultSet.next();

                Coupon coupon = new Coupon();
                coupon.setId(resultSet.getInt(1));
                coupon.setCompanyId(resultSet.getInt(2));

                for (Category category : Category.values()) {
                    if(category.getCode() == resultSet.getInt(3)){
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

            resultSet.close();
            stm.close();
            return coupon;

        }catch (SQLException e) {
            throw new CouponSystemException("getOneCoupon error at CouponDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }

    }

    /**
     * addCouponPurchase - inserts a record to CUSTOMERS_VS_COUPONS table
     * a customer purchase
     * update the amount of coupons
     */
    @Override
    public synchronized void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {

        String SQL = "insert into CUSTOMERS_VS_COUPONS values(?,?)";

        Connection con = ConnectionPool.getInstance().getConnection();
        try{

            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, couponId);
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            System.out.println(resultSet.next());
            Coupon coupon = getOneCoupon(couponId);
            changeCouponAmount(couponId,coupon.getAmount()-1);

            ConnectionPool.getInstance().restoreConnection(con);

        } catch (SQLException e) {
            throw new CouponSystemException("addCouponPurchase error at CouponDBDAO ",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }
    }

    /**
     * deleteCouponPurchase - delete record from CUSTOMERS_VS_COUPONS according to customer id and coupon id
     */
    @Override
    public synchronized void deleteCouponPurchase(int customerId, int couponId) throws CouponSystemException {
        String sql = "delete from CUSTOMERS_VS_COUPONS where CUSTOMER_ID  =? AND COUPON_ID =? ";
        Connection con = ConnectionPool.getInstance().getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, couponId);
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            Coupon coupon = getOneCoupon(couponId);
            pstmt.close();
            changeCouponAmount(couponId,coupon.getAmount()+1);
        } catch (SQLException e) {
            throw new CouponSystemException("deleteCouponPurchase error at couponDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * changeCouponAmount - chnges the amount of available coupons
     */
    @Override
        public synchronized void changeCouponAmount(int couponId , int amountChange) throws CouponSystemException {
                String sql = "UPDATE coupons SET AMOUNT =? WHERE id = ?";
        Connection con = ConnectionPool.getInstance().getConnection();
        try(
            PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1,amountChange);
            ps.setInt(2,couponId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CouponSystemException("changeCouponAmount error at CouponDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * uniqueTitleByCompany - return true/false if there is a coupon with companyId and a certain title
     */
    @Override
    public synchronized boolean uniqueTitleByCompany(int companyId,String title ) throws CouponSystemException {
        String sql = "select * from coupons where  COMPANY_ID = "+ companyId +" AND TITLE = '" + title+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            boolean result = resultSet.next();
            resultSet.close();
            stm.close();
            return result;

        }catch (SQLException e) {
            throw new CouponSystemException("uniqueTitleByCompany error at CouponDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * doesCustomerPurchaseExist - return true/false if there is a record at CUSTOMERS_VS_COUPONS
     * with given customerId and couponId
     */
    public synchronized boolean doesCustomerPurchaseExist(int customerId, int couponId ) throws CouponSystemException{
       String sql  =  "select * from CUSTOMERS_VS_COUPONS where CUSTOMER_ID = ? and  COUPON_ID = ?";

        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement  ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);
            ps.setInt(2, couponId);

        ResultSet rs = ps.executeQuery();
        return rs.next();

        } catch (SQLException e) {
            throw new CouponSystemException("doesCustomerPurchaseExist error at CouponDBDAO ",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }

    }



}
