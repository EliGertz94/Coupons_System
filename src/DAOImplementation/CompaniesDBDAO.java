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

    /**
     * isCompanyExists - return true/false if company user can log in
     */
    @Override
    public synchronized boolean isCompanyExists(String email, String password) throws CouponSystemException {

        String sql = "select * from company where email = '" +
                email + "'" + " AND password = '" + password + "'";
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();

        Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet = stm.executeQuery(sql);

           boolean result  =resultSet.next();
            resultSet.close();
            stm.close();
            ConnectionPool.getInstance().restoreConnection(con);
            return result ;

        } catch (SQLException e) {
            throw new CouponSystemException("isCompanyExists sql excepting at companyDBDAO",e);
        }



    }
    /**
     * companyByLogin - return the company object acording to the login details
     * also to later get the id of the object
     */
    public synchronized Company companyByLogin(String email, String password) throws CouponSystemException {

        String sql = "select * from company where email = '" +
                email + "'" + " AND password = '" + password + "'";
        Connection con = ConnectionPool.getInstance().getConnection();
        Statement stm = null;
        try {
            stm = con.createStatement();
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

            return company;

        } catch (SQLException e) {
            throw new CouponSystemException("companyByLogin - SQLException at companyDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }
    }

    /**
     * addCompany - add/insert company to DB
     */
    @Override
    public synchronized int addCompany(Company company) throws CouponSystemException {

        String SQL = "insert into company(name,email,password) values(?,?,?)";
        Connection con = ConnectionPool.getInstance().getConnection();

          try{
                PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);
              System.out.println("try1");
                pstmt.setString(1, company.getName());
                pstmt.setString(2, company.getEmail());
                pstmt.setString(3, company.getPassword());
                pstmt.executeUpdate();
              System.out.println("try2");

              ResultSet resultSet = pstmt.getGeneratedKeys();
                resultSet.next();
                int id= resultSet.getInt(1);
                pstmt.close();
                return id;

            } catch (SQLException e) {
                throw new CouponSystemException("add company error at companyDBDAO");

            }finally {
              ConnectionPool.getInstance().restoreConnection(con);

          }


    }

    /**
     * updateCompany - update company on DB
     */
    @Override
    public synchronized void updateCompany(Company company) throws CouponSystemException {
        String sql = "UPDATE company SET name= ?, email = ?, password=? WHERE id = ?";
        Connection con = ConnectionPool.getInstance().getConnection();
           try {
               PreparedStatement ps = con.prepareStatement(sql);

            // set the preparedstatement parameters
            ps.setString(1,company.getName());
            ps.setString(2,company.getEmail());
            ps.setString(3,company.getPassword());
            ps.setInt(4,company.getId());


            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
           throw new CouponSystemException("update error at Company at companyDBDAO");
        }finally {
               ConnectionPool.getInstance().restoreConnection(con);

           }
    }


    /**
     * deleteCompany - delete company from DB
     */
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

        } catch (SQLException e) {
            throw new CouponSystemException("delete company error at CompanyDBDAO");
        }
    }

    /**
     * deleteFromCoupons - delete coupon according to company id
     */
    @Override
    public  synchronized void deleteFromCoupons(int companyId) throws CouponSystemException{
        String sql = "delete from coupons where COMPANY_ID  = " + companyId;
        Connection con = ConnectionPool.getInstance().getConnection();
        try{
            System.out.println(" is it closed "+con.isClosed());

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);

            System.out.println("amount of rows effected "+ rawCount);

        } catch (SQLException e) {
            throw new CouponSystemException("delete coupon by company id  error  at CompanyDBDAO ",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }
    }

    /**
     * deleteFromCVC - delete from CUSTOMERS_VS_COUPONS table according to company id
     */
    @Override
    public synchronized void deleteFromCVC(int companyId) throws CouponSystemException{
        String sql = "delete from CUSTOMERS_VS_COUPONS where COUPON_ID  = " + companyId;
        Connection con = ConnectionPool.getInstance().getConnection();
        try {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            System.out.println("amount of rows effected "+ rawCount);

        } catch (SQLException e) {
            throw new CouponSystemException("deleteFromCVC error at CompanyDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * getAllCompanies - returns an arraylist of type company object containing all the companies in the company table
     */
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
            ConnectionPool.getInstance().restoreConnection(con);
            return companies;

        }catch (SQLException e) {
        throw new CouponSystemException("getAllCompanies error at CompanyDBDAO ",e);
    }


    }

    /**
     * getOneCompany - returns one company object by company id
     */
    @Override
    public synchronized Company getOneCompany(int companyId) throws CouponSystemException {

        String sql = "select * from company where id = '"+ companyId+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try{
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            Company company = new Company();
            if(resultSet.next()) {
               company.setId(resultSet.getInt(1));
               company.setName(resultSet.getString(2));
               company.setEmail(resultSet.getString(3));
               company.setPassword(resultSet.getString(4));
           }
            resultSet.close();
            stm.close();

            return company;

        }catch (SQLException e) {
            throw new CouponSystemException("getOneCompany error at CompanyDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }

    /**
     * getCompanyByName - returns true/false if a company with a given companyName exist
     */
    @Override
    public synchronized boolean getCompanyByName(String companyName) throws CouponSystemException {

        String sql = "Select  * from company where name =  '"+ companyName+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            boolean result=  resultSet.next();

            resultSet.close();
            stm.close();

            return result;

        }catch (SQLException  e) {
            throw new CouponSystemException("getCompanyByName error at CompanyDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }

    /**
     * getCompanyByName - returns true/false if a company with a given companyEmail exist
     */
    public synchronized boolean getCompanyByEmail(String companyEmail) throws CouponSystemException {

        String sql = "select * from company where email = '"
                + companyEmail.replaceAll(" ", "")+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try{
            System.out.println(con.isClosed());

            System.out.println(companyEmail);
            Statement stm = con.createStatement();
            System.out.println(stm);

            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            boolean result=  resultSet.next();
            System.out.println("b");

            resultSet.close();
            stm.close();


            return result;

        }catch (SQLException e) {
            throw new CouponSystemException("getCompanyByEmail error at CompanyDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }

    }

}
