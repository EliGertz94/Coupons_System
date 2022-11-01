package DAOImplementation;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CustomersDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {

    /**
     * isCustomerExists - return a customer object based on the login details
     */
    @Override
    public synchronized Customer isCustomerExists(String email, String password) throws CouponSystemException {

        String sql = "select * from customer where email = '" +
                email + "'" + " AND password = '" + password + "'";

        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();

            stm.execute(sql);
            ResultSet resultSet = stm.executeQuery(sql);
            Customer customer = new Customer();
            if( resultSet.next()) {
                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));

                customer.setEmail(resultSet.getString(4));
                customer.setPassword(resultSet.getString(5));
                resultSet.close();
                stm.close();
            }
            return customer;

        } catch (SQLException e) {
            throw new CouponSystemException("isCustomerExists error at CustomerDBDAO",e);
        }finally {

            ConnectionPool.getInstance().restoreConnection(con);
        }
    }

    /**
     * addCustomer - added a customer record
     * returns the id of a generated record
     */
    @Override
    public synchronized int addCustomer(Customer customer) throws CouponSystemException {

        String SQL = "insert into customer(FIRST_NAME,LAST_NAME,email,password) values(?,?,?,?)";

        Connection con = ConnectionPool.getInstance().getConnection();
        try{

            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPassword());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            int id= resultSet.getInt(1);

            return id;

        } catch (SQLException e) {
            throw new CouponSystemException("addCustomer error at CustomerDBDOA",e);

        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }


    }


    /**
     * updateCustomer -update a customer record
     */
    @Override
    public synchronized void updateCustomer(Customer customer) throws  CouponSystemException {
        String sql = "UPDATE customer SET FIRST_NAME = ?,LAST_NAME = ?, email = ?, password=? WHERE id = ?";
        Connection con = ConnectionPool.getInstance().getConnection();
        try{
            PreparedStatement ps = con.prepareStatement(sql);

            // set the preparedstatement parameters
            ps.setString(1,customer.getFirstName());
            ps.setString(2,customer.getLastName());
            ps.setString(3,customer.getEmail());
            ps.setString(4,customer.getPassword());
            ps.setInt(5,customer.getId());


            ps.executeUpdate();

        } catch (SQLException  e) {
            throw new CouponSystemException("updateCustomer error at CustomerDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }
    }

    /**
     * deleteCustomer - delete a customer by customerId
     */
    @Override
    public synchronized void deleteCustomer(int customerId) throws CouponSystemException {
        String sql = "delete from customer where id  = " + customerId;
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("no customer with such id ");
            }else{
                System.out.println(customerId+ " was deleted");
            }



        } catch (SQLException e) {
            throw new CouponSystemException("deleteCustomer error at CustomersDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }



    /**
     * getAllCustomers - returns a arrayList of type Customer
     */
    @Override
    public synchronized ArrayList<Customer> getAllCustomers() throws CouponSystemException {

        String sql = "select * from customer";
        ArrayList<Customer> customers  = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setEmail(resultSet.getString(4));
                customer.setPassword(resultSet.getString(5));
                customers.add(customer);
            }

            resultSet.close();
            stm.close();
            return customers;

        }catch (SQLException e) {
            throw new CouponSystemException("getAllCustomers error at CustomersDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }


    }

    /**
     * getOneCustomer - returns a customer object by customerId
     */
    @Override
    public synchronized Customer getOneCustomer(int customerId) throws CouponSystemException {

        String sql = "select * from customer where id = "+ customerId;
        Connection con = ConnectionPool.getInstance().getConnection();

        try {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            resultSet.next();
            Customer customer = new Customer();
            customer.setId(resultSet.getInt(1));
            customer.setFirstName(resultSet.getString(2));
            customer.setLastName(resultSet.getString(3));
            customer.setEmail(resultSet.getString(4));
            customer.setPassword(resultSet.getString(5));

            resultSet.close();
            stm.close();

            return customer;

        }catch (SQLException e) {
            throw new CouponSystemException("getOneCustomer error at CustomersDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }

    }
    /**
     * getCustomerByEmail -  returns true/false if customer exist with given email
     */
    @Override
    public synchronized boolean getCustomerByEmail(String customerEmail) throws CouponSystemException {

        String sql = "select * from customer where email = '"+ customerEmail.replaceAll(" ", "")+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try(
            Statement stm = con.createStatement();) {
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);

            boolean result=  resultSet.next();

            resultSet.close();
            stm.close();

            if (result) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            throw new CouponSystemException("getCustomerByEmail error at CustomersDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException {

        String sql = "select * from coupons where coupons.id  in " +
                "(select COUPON_ID from CUSTOMERS_VS_COUPONS" +
                " where CUSTOMER_ID =" + customerId + ")";
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try  {
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


        }catch (SQLException e ){
            throw new CouponSystemException("getCustomerCoupons on Customer Facade");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    @Override
    public  ArrayList<Coupon> getCustomerCoupons(double maxPrice,int customerId) throws CouponSystemException {


        String sql = "select * from coupons where coupons.id  in " +
                "(select COUPON_ID from CUSTOMERS_VS_COUPONS" +
                " where CUSTOMER_ID =" + customerId + ")  AND coupons.PRICE <= "+maxPrice;
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
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


        }catch(SQLException e){
            throw new CouponSystemException("getCustomerCoupons max price at Customer Facade");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }

    }

    public  ArrayList<Coupon> getCustomerCoupons(Category couponCategory,int customerId) throws CouponSystemException {


        String sql = "select * from coupons where coupons.id  in " +
                "(select COUPON_ID from CUSTOMERS_VS_COUPONS" +
                " where CUSTOMER_ID =" + customerId + ")  AND coupons.CATEGORY_ID= "+couponCategory.getCode();
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try  {
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


        }catch (SQLException e){
            throw new CouponSystemException("getCustomerCoupons by category ");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }

    }




}
