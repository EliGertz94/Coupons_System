package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import ConnectionPoolRelated.ConnectionPool;
import Exceptions.CouponSystemException;

import java.sql.*;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade{

    private int companyId;


// how to show exception when getting the object
    public synchronized boolean logIn(String email, String password) throws CouponSystemException {

        try {
            this.companyId=companiesDAO.companyByLogin(email,password).getId();
        boolean result  =   companiesDAO.isCompanyExists(email,password);
            return result;
        } catch (CouponSystemException e) {
           throw new CouponSystemException("logIn at CompanyFacade",e);
        }
    }

    //הוספת קופון חדש.
    //o אין להוסיף קופון בעל כותרת זהה לקופון קיים של אותה החברה. מותר להוסיף קופון
    //בעל כותרת זהה לקופון של חברה אחרת.



    public synchronized void addCoupon(Coupon coupon) throws CouponSystemException {
        coupon.setCompanyId(this.companyId);

        if(!couponsDAO.uniqueTitleByCompany(coupon.getCompanyId(),coupon.getTitle())){
            couponsDAO.addCoupon(coupon);
        }else {
            System.out.println("this title for a coupon exist already");
        }

    }

    //עדכון קופון קיים.
    //o לא ניתן לעדכן את קוד הקופון.
    // o לא ניתן לעדכן את קוד החברה.

    //should i check validity of coupon ?
    public synchronized void updateCoupon(Coupon coupon) throws CouponSystemException {

        coupon.setCompanyId(this.companyId);
        couponsDAO.updateCoupon(coupon);
    }

    //מחיקת קופון
    //o יש למחוק בנוסף גם את היסטוריית רכישת הקופון ע"י לקוחות.
    public synchronized void deleteCoupon(int couponId) throws CouponSystemException {
        couponsDAO.deleteCoupon(couponId);
    }

    public  synchronized ArrayList<Coupon> getAllCompanyCoupons() throws CouponSystemException {

        String sql = "select * from coupons WHERE COMPANY_ID = " + this.companyId;
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

    public synchronized ArrayList<Coupon> getAllCompanyCoupons(Category category) throws CouponSystemException {

        String sql = "select * from coupons WHERE COMPANY_ID = " + this.companyId+" AND CATEGORY_ID = " +category.getCode();
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

                for (Category category1 : Category.values()) {
                    if(category1.getCode() == resultSet.getInt(3)){
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

        }catch (SQLException  e) {
            throw new CouponSystemException("delete exception");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }


    public synchronized ArrayList<Coupon> getAllCompanyCoupons(double maxPrice) throws CouponSystemException {
        String sql = "select * from coupons WHERE COMPANY_ID = " + this.companyId+" AND PRICE <= " +maxPrice;
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

                for (Category category1 : Category.values()) {
                    if(category1.getCode() == resultSet.getInt(3)){
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

        }catch (SQLException  e) {
            throw new CouponSystemException("max price");
        }finally {
            ConnectionPool.getInstance().restoreConnection(con);

        }


    }
    public synchronized Company getCompanyDetails( ) throws CouponSystemException {

       return companiesDAO.getOneCompany(this.getCompanyId());
    }


        public int getCompanyId() {
        return companyId;
    }
}
