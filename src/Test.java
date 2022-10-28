import Beans.Company;
import ClientLogIn.ClientType;
import ClientLogIn.LoginManager;
import DAOImplementation.CompaniesDBDAO;
import DAOImplementation.test;
import Exceptions.CouponSystemException;
import Facade.AdminFacade;
import Job.CouponExpirationDailyJob;

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
             //Company( String name, String email, String password)
             try {
                 AdminFacade admin = (AdminFacade) LoginManager.getInstance().logIn("admin@admin.com","admin", ClientType.Administrator);
                   admin.addCompany(new Company("B in","B@gmail.com","123456A"));
                   admin.addCompany(new Company("B 22in","B33@gmail.com","12323456A"));
                   Company company=  admin.getOneCompany(6);
                     company.setEmail("12@gmail.com");
                    // company.setName("12dw");
                 //update after changing email can't change when the name is diffrent
                     admin.updateCompany(company);
                     Company company2 = admin.getOneCompany(3);

                     admin.deleteCompany(company2);






















             } catch (CouponSystemException e) {
                 throw new RuntimeException(e);
             }finally {

                 couponJob.stopJob();
             }

         }


}
