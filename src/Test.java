import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ClientLogIn.ClientType;
import ClientLogIn.LoginManager;
import ConnectionPoolRelated.ConnectionPool;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.CouponsDBDAO;
import DAOImplementation.test;
import Exceptions.CouponSystemException;
import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import Job.CouponExpirationDailyJob;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Test {

    //א. הפעלת ה-Job היומי.
    // ב. התחברות ע"י ה-LoginManager כ-Administrator, קבלת אובייקט AdminFacade וקריאה לכל פונקציית
    //לוגיקה עסקית שלו.
    // ג. התחברות ע"י ה-LoginManager כ-Company, קבלת אובייקט CompanyFacade וקריאה לכל פונקציית
    //לוגיקה עסקית שלו.
    // ד. התחברות ע"י ה-LoginManager כ-Customer, קבלת אובייקט CustomerFacade וקריאה לכל פונקציית
    //לוגיקה עסקית שלו. ה. הפסקת ה-Job היומי. ו. סגירת כל ה-Connections (קריאה לפונקציה closeAllConnections של ה-ConnectionPool).


   public static void main(String[] args) {
      testAll();
   }
         public static void testAll(){
             /**
              * start job
              */
            CouponExpirationDailyJob couponJob =  new CouponExpirationDailyJob();
            couponJob.start();

             /**
              * Administrator login
              */
             try {
                      AdminFacade admin = (AdminFacade) LoginManager.getInstance().logIn("admin@admin.com","admin", ClientType.Administrator);
                       admin.addCompany(new Company("B 22in","B33@gmail.com","12323456A"));
                       Company company=  admin.getOneCompany(6);
                       company.setEmail("12@gmail.com");
                        // company.setName("12dw");
                    //update after changing email can't change when the name is diffrent
                     admin.updateCompany(company);
                     //delete company
                     Company company2 = admin.getOneCompany(3);
                     admin.deleteCompany(company2);
                     System.out.println(admin.getAllCompanies());
                     Customer customer = new Customer("Avi","shmueli","AS@gmail.com","AV1234");
                     admin.addCustomer(customer);
                     Customer customerAvi =  admin.getOneCustomer(7);
                     customerAvi.setLastName("Shemesh");
                     admin.updateCustomer(customerAvi);
                     admin.deleteCustomer(4);
                     System.out.println(admin.getAllCustomers());

                  //-------------- CompanyFacade --------------

                  CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().logIn("12@gmail.com","1111E006A",ClientType.Company);


                 // get a coupon object from a CouponsDBDAO method (shorter that way)
                 CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

                 int couponId  =33;
                 Coupon  coupon =  new Coupon();
                 if(couponsDBDAO.doesCouponExists(couponId)){
                       coupon = couponsDBDAO.getOneCoupon(couponId);
                 }

                 //add a coupon
                 companyFacade.addCoupon(coupon);
                 coupon.setDescription("description of coupon");
                 coupon.setCategory(Category.Food);

                 //update changes
                 companyFacade.updateCoupon(coupon);
                 //----delete a coupon by id
                 //will delete purchases as well thanks to - ON DELETE CASCADE SQL
                // companyFacade.deleteCoupon(30);
                 //----return all the coupons of the company
                 ArrayList<Coupon> allCompanyCoupons= companyFacade.getAllCompanyCoupons();
                 ArrayList<Coupon> CompanyCouponsByCategory= companyFacade.getAllCompanyCoupons(Category.Electricity);
                 ArrayList<Coupon> companyCouponsByMaxPrice=companyFacade.getAllCompanyCoupons(150);
                 //get logged in company details
                 System.out.println(companyFacade.getCompanyDetails());

                 //----------- customerFacade -----------

                 CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().logIn
                         ("email1@gmail.com","password",ClientType.Customer);

                 //purchaseCoupon
                 customerFacade.purchaseCoupon(35);

                 //get customer coupons
                 ArrayList<Coupon> customersCoupons = customerFacade.getCustomerCoupons();

                 //get customer coupons by max price
                 ArrayList<Coupon> customerCouponByMaxPrice = customerFacade.getCustomerCoupons(200);
                 //get customer coupons by Category
                 ArrayList<Coupon> customerCouponByCategory = customerFacade.getCustomerCoupons(Category.Food);

                 //customer details
                 Customer customer1=  customerFacade.getCustomerDetails();
                 System.out.println(customer1);

                 couponJob.stopJob();

             } catch (CouponSystemException e) {
                 throw new RuntimeException(e);
             }finally {

                 try {
                     ConnectionPool.getInstance().closeAllConnections();
                 } catch (CouponSystemException e) {
                     throw new RuntimeException(e);
                 }
             }

         }


}
