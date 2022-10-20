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



    @Override
    public synchronized boolean doesCouponExists(int couponId) {

        try {
            Connection   connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement ps =
                    connection.prepareStatement("SELECT id FROM coupons WHERE id = ?");
            ps.setInt(1, couponId);

            ResultSet rs = ps.executeQuery();
            ConnectionPool.getInstance().restoreConnection(connection);

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


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
        try{

            Connection con = ConnectionPool.getInstance().getConnection();
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
                System.out.println("coupon with id number " + id + " was created");

            ConnectionPool.getInstance().restoreConnection(con);
         return id;

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("add company error");

        }

    }

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
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);) {

            // set the preparedstatement parameters
          //  ps.setInt(1,coupon.getId());
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
            throw new CouponSystemException("update SQLException");
        }
        catch (CouponSystemException e) {
            throw new CouponSystemException("update coupon");
        }
    }

    @Override
    public synchronized void deleteCoupon(int couponId) throws CouponSystemException {
        String sql = "delete from coupons where id  = " + couponId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("deleteCoupon - no rows were effected ");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }
    }

    @Override
    public synchronized ArrayList<Coupon> getAllCoupons() throws CouponSystemException {

        String sql = "select * from coupons";
        ArrayList<Coupon> coupons  = new ArrayList<>();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
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

        }catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }


    }

    @Override
    public synchronized Coupon getOneCoupon(int couponId) throws CouponSystemException {

        String sql = "select * from coupons where id = "+ couponId;
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
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

        }catch (CouponSystemException | SQLException e) {
            throw new CouponSystemException("delete exception");
        }

    }

    @Override
    public synchronized void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {

        String SQL = "insert into CUSTOMERS_VS_COUPONS values(?,?)";


        try{

            Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, couponId);
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            System.out.println(resultSet.next());
            // should I return connection every time or does it do it automatically
            Coupon coupon = getOneCoupon(couponId);
            changeCouponAmount(couponId,coupon.getAmount()-1);

            ConnectionPool.getInstance().restoreConnection(con);


        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("addCouponPurchase  was added ");

        }


    }

    @Override
    public synchronized void deleteCouponPurchase(int customerId, int couponId) throws CouponSystemException {
        String sql = "delete from CUSTOMERS_VS_COUPONS where CUSTOMER_ID  =? AND COUPON_ID =? ";
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            PreparedStatement pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, couponId);
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            Coupon coupon = getOneCoupon(couponId);

            changeCouponAmount(couponId,coupon.getAmount()+1);
        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }
    }

    @Override
        public synchronized void changeCouponAmount(int couponId , int amountChange) throws CouponSystemException {
                String sql = "UPDATE coupons SET AMOUNT =? WHERE id = ?";

                try(Connection con = ConnectionPool.getInstance().getConnection();
                    PreparedStatement ps = con.prepareStatement(sql);) {
                    ps.setInt(1,amountChange);
                    ps.setInt(2,couponId);
                    ps.executeUpdate();

                } catch (SQLException | CouponSystemException e) {
                    throw new CouponSystemException("update error at Company");
                }
        }


    @Override
    public synchronized boolean uniqueTitleByCompany(int companyId,String title ) throws CouponSystemException {
        String sql = "select * from coupons where  COMPANY_ID = "+ companyId +" AND TITLE = '" + title+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            boolean result = resultSet.next();
            resultSet.close();
            stm.close();
            return result;

        }catch (CouponSystemException | SQLException e) {
            throw new CouponSystemException("delete exception");
        }
    }

    public synchronized boolean doesCustomerPurchesExsist(int customerId, int couponId ){
       String sql  =  "select * from CUSTOMERS_VS_COUPONS where CUSTOMER_ID = ? and  COUPON_ID = ?";
        Connection   connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();



            PreparedStatement  ps = connection.prepareStatement(sql);

            ps.setInt(1, customerId);
            ps.setInt(2, couponId);

        ResultSet rs = ps.executeQuery();
        ConnectionPool.getInstance().restoreConnection(connection);

        return rs.next();

        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
