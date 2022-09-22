package DAOImplementation;

import Beans.Company;
import Beans.Customer;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CustomersDAO;
import Exceptions.CouponSystemException;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {
    @Override
    public boolean isCustomerExists(String email, String password) {

            try {
                Connection   connection = ConnectionPool.getInstance().getConnection();

                PreparedStatement ps =
                        connection.prepareStatement("SELECT id FROM customer WHERE password = ? AND email = ?");
                ps.setString (1, password);
                ps.setString (2, email);
                ResultSet rs = ps.executeQuery();

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
    public int addCustomer(Customer customer) throws CouponSystemException {

        String SQL = " insert into customer(FIRST_NAME,LAST_NAME,email,password) values(?,?,?,?)";

        try{

            Connection con = ConnectionPool.getInstance().getConnection();
            System.out.println(con);
            PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPassword());
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

    //create table `customer`(`id` int NOT NULL PRIMARY KEY auto_increment,
    // `FIRST_NAME` varchar(25),
    //  `LAST_NAME` varchar(25),
    // `email` varchar(50) UNIQUE NOT NULL,
    // `password` varchar(60) NOT NULL );

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
    public void deleteCustomer(int customerId) {

    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Company getOneCustomer(int companyId) {
        return null;
    }
}
