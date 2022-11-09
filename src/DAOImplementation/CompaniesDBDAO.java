package DAOImplementation;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import ConnectionPoolRelated.ConnectionPool;
import DAO.CompaniesDAO;
import Exceptions.CouponSystemException;

import java.sql.*;
import java.util.ArrayList;


public class CompaniesDBDAO implements CompaniesDAO {

    /**
     * isCompanyExists - return true/false if company user can log in
     */
    @Override
    public  boolean isCompanyExists(String email, String password) throws CouponSystemException {

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
            return result ;

        } catch (SQLException e) {
            throw new CouponSystemException("isCompanyExists sql excepting at companyDBDAO",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }



    }
    /**
     * companyByLogin - return the company object acording to the login details
     * also to later get the id of the object
     */
    public  Company companyByLogin(String email, String password) throws CouponSystemException {

        String sql = "select * from company where email = '" +
                email + "'" + " AND password = '" + password + "'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
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
    public  int addCompany(Company company) throws CouponSystemException {

        String SQL = "insert into company(name,email,password) values(?,?,?)";
        Connection con = ConnectionPool.getInstance().getConnection();

          try{
                PreparedStatement pstmt = con.prepareStatement(SQL,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, company.getName());
                pstmt.setString(2, company.getEmail());
                pstmt.setString(3, company.getPassword());
                pstmt.executeUpdate();

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
    public  void updateCompany(Company company) throws CouponSystemException {
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
     * from all the child
     */
    @Override
    public  void deleteCompany(int companyId) throws CouponSystemException {

        deleteFromCVC(companyId);
        deleteFromCoupons(companyId);
        String sql = "DELETE  FROM company  WHERE id= '" + companyId+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try {

            Statement stm = con.createStatement();
            int rawCount =  stm.executeUpdate(sql);

            System.out.println("amount of the rows effected is "+ rawCount );

        } catch (SQLException e) {
            throw new CouponSystemException("delete company error at CompanyDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * deleteFromCoupons - delete coupon according to company id
     */
    @Override
    public   void deleteFromCoupons(int companyId) throws CouponSystemException{
        String sql = "delete from coupons where COMPANY_ID  = " + companyId;
        Connection con = ConnectionPool.getInstance().getConnection();
        try{

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
    public  void deleteFromCVC(int companyId) throws CouponSystemException{
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
    public  ArrayList<Company> getAllCompanies() throws  CouponSystemException {

        String sql = "select * from company";
        ArrayList<Company> companies  = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
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
            throw new CouponSystemException("getAllCompanies error at CompanyDBDAO ",e);
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }

    /**
     * getOneCompany - returns one company object by company id
     */
    @Override
    public  Company getOneCompany(int companyId) throws CouponSystemException {

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
    public  boolean getCompanyByName(String companyName) throws CouponSystemException {

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
    @Override
    public  boolean getCompanyByEmail(String companyEmail) throws CouponSystemException {

        String sql = "select * from company where email = '"
                + companyEmail.replaceAll(" ", "")+"'";
        Connection con = ConnectionPool.getInstance().getConnection();
        try{

            Statement stm = con.createStatement();

            stm.execute(sql);
            ResultSet resultSet=stm.executeQuery(sql);
            boolean result=  resultSet.next();

            resultSet.close();
            stm.close();


            return result;

        }catch (SQLException e) {
            throw new CouponSystemException("getCompanyByEmail error at CompanyDBDAO");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);
        }

    }


    /**
     * getAllCompanyCoupons - get a list of all the coupons according to companyId
     */
    @Override
    public  ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws CouponSystemException {

        String sql = "select * from coupons WHERE COMPANY_ID = " + companyId;
        ArrayList<Coupon> coupons  = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try{
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

        }catch (SQLException  e) {
            throw new CouponSystemException("delete exception");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }

    /**
     * getAllCompanyCoupons - get a list of all the coupons according to companyId and category
     */
    @Override
    public  ArrayList<Coupon> getAllCompanyCoupons(Category category , int companyId) throws CouponSystemException {

        String sql = "select * from coupons WHERE COMPANY_ID = " + companyId + " AND CATEGORY_ID = " + category.getCode();
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

                for (Category category1 : Category.values()) {
                    if (category1.getCode() == resultSet.getInt(3)) {
                        coupon.setCategory(category1);
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

        } catch (SQLException e) {
            throw new CouponSystemException("getAllCompanyCoupons exception on CompanyDBDAO");
        } finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }
    }

    /**
     * getAllCompanyCoupons - get a list of all the coupons according to companyId  and till a maxPrice
     */
    public  ArrayList<Coupon> getAllCompanyCoupons(double maxPrice,int couponId) throws CouponSystemException {
        String sql = "select * from coupons WHERE COMPANY_ID = " + couponId + " AND PRICE <= " + maxPrice;
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

                for (Category category1 : Category.values()) {
                    if (category1.getCode() == resultSet.getInt(3)) {
                        coupon.setCategory(category1);
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

        } catch (SQLException e) {
            throw new CouponSystemException("max price");
        } finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }

    }
    }
