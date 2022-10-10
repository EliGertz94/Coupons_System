package DAOImplementation;

import Beans.Company;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CustomersDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {

                      //  connection.prepareStatement("SELECT id FROM customer WHERE password = ? AND email = ?");


    @Override
    public Customer isCustomerExists(String email, String password) {

        String sql = "select * from customer where email = '" +
                email + "'" + " AND password = '" + password + "'";
        try (Connection con = ConnectionPool.getInstance().getConnection();) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet = stm.executeQuery(sql);
            Customer customer = new Customer();
            if(resultSet.next()) {
                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));

                customer.setEmail(resultSet.getString(4));
                customer.setPassword(resultSet.getString(5));
                resultSet.close();
                stm.close();
            }
            return customer;

        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public int addCustomer(Customer customer) throws CouponSystemException {

        String SQL = " insert into customer(FIRST_NAME,LAST_NAME,email,password) values(?,?,?,?)";

        try{

            Connection con = ConnectionPool.getInstance().getConnection();

            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPassword());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            int id= resultSet.getInt(1);

            ConnectionPool.getInstance().restoreConnection(con);
            return id;

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("add company error");

        }


    }



    @Override
    public void updateCustomer(Customer customer) throws  CouponSystemException {
        String sql = "UPDATE customer SET FIRST_NAME = ?,LAST_NAME = ?, email = ?, password=? WHERE id = ?";
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);) {

            // set the preparedstatement parameters
            ps.setString(1,customer.getFirstName());
            ps.setString(2,customer.getLastName());
            ps.setString(3,customer.getEmail());
            ps.setString(4,customer.getPassword());
            ps.setInt(5,customer.getId());


            ps.executeUpdate();
        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("update error at Company");
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        String sql = "delete from customer where id  = " + customerId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("no customer with such id ");
            }else{
                System.out.println(customerId+ " was deleted");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }
    }


    //create table `customer`(`id` int NOT NULL PRIMARY KEY auto_increment,
    // `FIRST_NAME` varchar(25),
    //  `LAST_NAME` varchar(25),
    // `email` varchar(50) UNIQUE NOT NULL,
    // `password` varchar(60) NOT NULL );

    @Override
    public ArrayList<Customer> getAllCustomers() throws CouponSystemException {

        String sql = "select * from customer";
        ArrayList<Customer> customers  = new ArrayList<>();
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
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
            throw new CouponSystemException("delete exception");
        }


    }

    @Override
    public Customer getOneCustomer(int customerId) throws CouponSystemException {

        String sql = "select * from customer where id = "+ customerId;
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
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
            throw new CouponSystemException("get one customer");
        }

    }

    @Override
    public boolean getCustomerByEmail(String companyEmail) throws CouponSystemException {

        String sql = "select * from customer where email = '"+ companyEmail.replaceAll(" ", "")+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection();
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


        }catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("getCompanyByEmail");
        }


    }


}
