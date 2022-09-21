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
    public boolean isCompanyExists(String email, String password)  {

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

        PreparedStatement ps =
                connection.prepareStatement("SELECT id FROM company WHERE password = ? AND email = ?");
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
    public int addCompany(Company company) throws ConnectException {

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
                throw new ConnectException("add company error");

            }


    }

    @Override
    public void updateCompany(Company company) throws ConnectException {
        String sql = "UPDATE company SET name = ?, email = ?, password=? WHERE id = ?";
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);) {

            // set the preparedstatement parameters
            ps.setString(1,company.getName());
            ps.setString(2,company.getEmail());
            ps.setString(3,company.getPassword());
            ps.setInt(4,company.getId());


            ps.executeUpdate();
        } catch (SQLException | CouponSystemException e) {
           throw new ConnectException("update error at Company");
        }
    }

    //delete from CUSTOMERS_VS_COUPONS where COUPON_ID  =1;
    //delete from coupons where COMPANY_ID  =1;
    //delete from company where id  =1;

    @Override
    public void deleteCompany(int companyId) throws ConnectException {

        deleteFromCVC(companyId);
        deleteFromCCoupons(companyId);
        String sql = "DELETE  FROM company  WHERE id= '" + companyId+"'";
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                throw new ConnectException("deleteCompany - no rows were effected ");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new ConnectException("delete exception");
        }
    }

    public void deleteFromCCoupons(int companyId) throws ConnectException{
        String sql = "delete from coupons where COMPANY_ID  = " + companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("deleteFromCVC - no rows were effected ");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new ConnectException("delete exception");
        }
    }

    public void deleteFromCVC(int companyId) throws ConnectException{
        String sql = "delete from CUSTOMERS_VS_COUPONS where COUPON_ID  = " + companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                System.out.println("deleteFromCVC - no rows were effected ");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new ConnectException("delete exception");
        }
    }





    public void deleteCompanyCoupons(int companyId) throws ConnectException {
        String sql = "delete from CUSTOMERS_VS_COUPONS where coupons COMPANY_ID = " + companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);
            if(rawCount ==0){
                throw new ConnectException("delete exception ");
            }

        } catch (SQLException | CouponSystemException e) {
            throw new ConnectException("delete exception");
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies() throws  CouponSystemException {

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
            return companies;

        }catch (SQLException e) {
        throw new CouponSystemException("delete exception");
    }


    }

    @Override
    public Company getOneCompany(int companyId) throws CouponSystemException, SQLException {

        String sql = "select * from company where id = "+ companyId;
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
            Statement stm = con.createStatement();
            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            resultSet.next();
                Company company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                company.setEmail(resultSet.getString(3));
                company.setPassword(resultSet.getString(4));

            resultSet.close();
            stm.close();
            return company;

        }catch (SQLException e) {
            throw new CouponSystemException("delete exception");
        }


    }
}
