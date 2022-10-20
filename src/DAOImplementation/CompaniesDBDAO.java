package DAOImplementation;

import Beans.Company;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import Exceptions.CouponSystemException;

import java.awt.print.Book;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDBDAO implements CompaniesDAO {

    @Override
    public synchronized boolean isCompanyExists(String email, String password) throws CouponSystemException, SQLException {

        String sql = "select * from company where email = '" +
                email + "'" + " AND password = '" + password + "'";
       Connection con = ConnectionPool.getInstance().getConnection();
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet = stm.executeQuery(sql);

           boolean result  =resultSet.next();
            resultSet.close();
           stm.close();
            ConnectionPool.getInstance().restoreConnection(con);
            return result ;


    }

    //return the company by the logint details
    // to later get the id of the login object company instance
    public synchronized Company companyByLogin(String email, String password) throws CouponSystemException, SQLException {

        String sql = "select * from company where email = '" +
                email + "'" + " AND password = '" + password + "'";
        Connection con = ConnectionPool.getInstance().getConnection();
        Statement stm = con.createStatement();
        stm.execute(sql);
        ResultSet resultSet = stm.executeQuery(sql);
        Company company = new Company();

           if( resultSet.next()) {
               company.setId(resultSet.getInt(1));
               company.setName(resultSet.getString(2));
               company.setEmail(resultSet.getString(3));
               company.setPassword(resultSet.getString(4));
           }
            resultSet.close();
            stm.close();
           // con.close();

        ConnectionPool.getInstance().restoreConnection(con);
        return company;
    }

        @Override
    public synchronized int addCompany(Company company) throws CouponSystemException {

            String SQL = " insert into company(name,email,password) values(?,?,?)";

          try{

                Connection con = ConnectionPool.getInstance().getConnection();
                System.out.println(con);
                PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, company.getName());
                pstmt.setString(2, company.getEmail());
                pstmt.setString(3, company.getPassword());
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
    public synchronized void updateCompany(Company company) throws CouponSystemException {
        String sql = "UPDATE company SET email = ?, password=? WHERE id = ?";
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            // set the preparedstatement parameters
            ps.setString(1,company.getEmail());
            ps.setString(2,company.getPassword());
            ps.setInt(3,company.getId());


            ps.executeUpdate();
            //returning connection was added
            ConnectionPool.getInstance().restoreConnection(con);
        } catch (SQLException | CouponSystemException e) {
           throw new CouponSystemException("update error at Company");
        }
    }

    //delete from CUSTOMERS_VS_COUPONS where COUPON_ID  =1;
    //delete from coupons where COMPANY_ID  =1;
    //delete from company where id  =1;

    @Override
    public synchronized void deleteCompany(int companyId) throws CouponSystemException {

        deleteFromCVC(companyId);
        deleteFromCoupons(companyId);
        String sql = "DELETE  FROM company  WHERE id= '" + companyId+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("no id was found - no item were deleted");
            }
            // returning connection was added
            ConnectionPool.getInstance().restoreConnection(con);

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }
    }

    @Override
    public  synchronized void deleteFromCoupons(int companyId) throws CouponSystemException{
        String sql = "delete from coupons where COMPANY_ID  = " + companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("deleteFromCVC - no rows were effected ");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }
    }

    @Override
    public synchronized void deleteFromCVC(int companyId) throws CouponSystemException{
        String sql = "delete from CUSTOMERS_VS_COUPONS where COUPON_ID  = " + companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
            }
            //returning connection was added
            ConnectionPool.getInstance().restoreConnection(con);
        } catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("delete exception");
        }
    }


    @Override
    public synchronized ArrayList<Company> getAllCompanies() throws  CouponSystemException {

        String sql = "select * from company";
        ArrayList<Company> companies  = new ArrayList<>();
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            while (resultSet.next()){
                Company company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                company.setEmail(resultSet.getString(3));
                company.setPassword(resultSet.getString(4));
                companies.add(company);


            }

            resultSet.close();
            stm.close();
            //returning connection was added
            ConnectionPool.getInstance().restoreConnection(con);
            return companies;

        }catch (SQLException e) {
        throw new CouponSystemException("getAllCompanies");
    }


    }

    @Override
    public synchronized Company getOneCompany(int companyId) throws CouponSystemException {

        String sql = "select * from company where id = '"+ companyId+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            Company company = new Company();

            if( resultSet.next()) {

                company.setId(resultSet.getInt(1));
               company.setName(resultSet.getString(2));
               company.setEmail(resultSet.getString(3));
               company.setPassword(resultSet.getString(4));
           }
            resultSet.close();
            stm.close();


            //return connection was added
            ConnectionPool.getInstance().restoreConnection(con);
            return company;

        }catch (SQLException e) {
            throw new CouponSystemException("getOneCompany");
        }


    }

    public synchronized boolean checkCompany(int companyId) throws CouponSystemException {
        String sql = "select * from company where id = "+ companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);

            //return connection was added
            ConnectionPool.getInstance().restoreConnection(con);
            return resultSet.next();
        }catch (SQLException e) {
            throw new CouponSystemException("getOneCompany");
        }
    }

    @Override
    public synchronized boolean getCompanyByName(String companyName) throws CouponSystemException {

        String sql = "Select  * from company where name =  '"+ companyName+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection();
           ) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            boolean result=  resultSet.next();

            resultSet.close();
            stm.close();
            //return connection was added
            ConnectionPool.getInstance().restoreConnection(con);
            if (result) {
                return true;
            } else {
                return false;
            }





        }catch (SQLException | CouponSystemException e) {
            throw new CouponSystemException("getCompanyByName");
        }


    }

    public synchronized boolean getCompanyByEmail(String companyEmail) throws CouponSystemException {

        String sql = "select * from company where email = '"+ companyEmail.replaceAll(" ", "")+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stm = con.createStatement();) {
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);

            boolean result=  resultSet.next();

            resultSet.close();
            stm.close();
            //return connection was added
            ConnectionPool.getInstance().restoreConnection(con);
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
