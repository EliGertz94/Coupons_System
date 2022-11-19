import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import ClientLogIn.ClientType;
import ClientLogIn.LoginManager;
import ConnectionPoolRelated.ConnectionPool;
import Exceptions.CouponSystemException;
import Facade.AdminFacade;

import Facade.CompanyFacade;
import Facade.CustomerFacade;
import Job.CouponExpirationDailyJob;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Test {

         public static void testAll() {
             /**
              * start job
              */
             CouponExpirationDailyJob couponJob = new CouponExpirationDailyJob();
             couponJob.startJob();

             /**
              * Administrator login
              *
              *                         ******!BEFORE YOU START!*******
              *         JOB - PLEASE SET THE CouponExpirationDailyJob AMOUNT OF TIME IN MILLISECONDS
              *         AT THE TEST - SET THE SLEEP OF THE THREAD AT THE BOTTOM BEFORE THE FINAL PART OF THE TRY CATCH
              */

             try {


                 //-----"Admin Login"-------

                 AdminFacade admin = (AdminFacade) LoginManager.getInstance().logIn("admin@admin.com", "admin", ClientType.Administrator);
                 admin.addCompany(new Company("aaa", "aaa@gmail.com", "aaa123"));
                 admin.addCompany(new Company("bbb", "bbb@gmail.com", "bbb123"));

                 //change the object
                 Company company1 = admin.getOneCompany(1);
                 company1.setEmail("AAA@gmail.com");
                 //company.setName("AAA1"); //can't change when the name is different

                 admin.updateCompany(company1);
                 //delete company
                 Company company2 = admin.getOneCompany(3);
                 admin.deleteCompany(company2.getId());

                 admin.addCustomer(new Customer("eli", "shmueli", "eli@gmail.com", "AV1234"));
                 admin.addCustomer(new Customer("avi", "shmueli", "avi@gmail.com", "AV1234"));
                 admin.addCustomer(new Customer("shalom", "shmueli", "shalom@gmail.com", "AV1234"));


                 admin.updateCustomer(new Customer("Aviyahu", "shmueli", "aviyahu@gmail.com", "AV1234"));

                 admin.deleteCustomer(3);

                 System.out.println("admin.getAllCustomers() "+ admin.getAllCustomers());



                 //-------------- CompanyFacade --------------
//
                 CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().logIn("AAA@gmail.com", "aaa123", ClientType.Company);


//
          //         ArrayList<Coupon> CompanyCouponsByCategory = companyFacade.getAllCompanyCoupons(Category.Electricity);
//
//
//
//
                   companyFacade.addCoupon(new Coupon
                           (companyFacade.getCompanyId(), Category.Food, "coupon1", "coupon description 1", LocalDateTime.now(), LocalDateTime.of(2022, 12, 12, 12, 12),
                                   100, 150, "String image"));
                 companyFacade.addCoupon(new Coupon
                         (companyFacade.getCompanyId(), Category.Electricity, "coupon2", "coupon description 2", LocalDateTime.now(), LocalDateTime.of(2022, 12, 12, 12, 12),
                                 100, 210, "String image"));
                 companyFacade.addCoupon(new Coupon
                         (companyFacade.getCompanyId(), Category.Restaurant, "coupon3", "coupon description 3", LocalDateTime.now(), LocalDateTime.of(2022, 11, 11, 12, 12),
                                 100, 200, "String image"));
                 companyFacade.addCoupon(new Coupon
                         (companyFacade.getCompanyId(), Category.Restaurant, "coupon4", "coupon description 4", LocalDateTime.now(), LocalDateTime.of(2022, 11, 11, 12, 12),
                                 100, 200, "String image"));

                 Coupon coupon = new Coupon
                         (companyFacade.getCompanyId(), Category.Restaurant, "coupon3 edited", "coupon description 3 edited",
                                 LocalDateTime.now(), LocalDateTime.of(2022, 11, 11, 12, 12),
                                 100, 200, "String image");
                 coupon.setId(3);

                 companyFacade.updateCoupon(coupon);

                 companyFacade.deleteCoupon(4);



              ArrayList<Coupon> companyCouponsByMaxPrice = companyFacade.getAllCompanyCoupons(250);


             System.out.println("companyCouponsByMaxPrice" + companyCouponsByMaxPrice);

                 Company companyDetails= companyFacade.getCompanyDetails();

                 System.out.println();

                 System.out.println("company details "+companyDetails);

//
//                 //       ----------- customerFacade -----------
                 CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().logIn
                         ("avi@gmail.com", "AV1234", ClientType.Customer);

                     customerFacade.purchaseCoupon(1);
                     customerFacade.purchaseCoupon(2);


                     //הוספתי את האופציה הזאת אפילו שלא היה אותה בהוראות (נראה לי סביר)

                      customerFacade.returnCouponPurchase(2);

                     //get customer coupons
                     ArrayList<Coupon> customersCoupons = customerFacade.getCustomerCoupons();

                     //get customer coupons by max price
                     ArrayList<Coupon> customerCouponByMaxPrice = customerFacade.getCustomerCoupons(150);

                     //get customer coupons by Category
                     ArrayList<Coupon> customerCouponByCategory = customerFacade.getCustomerCoupons(Category.Electricity);

                     //customer details
                     Customer customerDetails = customerFacade.getCustomerDetails();

                     System.out.println(" Customer details "+customerDetails);


                 //********* Time For The Test To Work ***********
                Thread.sleep(10_000);

             } catch (Exception e) {
                 e.printStackTrace();
                 System.out.println(e);
             } finally {
                 try {
                     couponJob.stopJob();
                     couponJob.getThread().join();
                     ConnectionPool.getInstance().closeAllConnections();
                 } catch (CouponSystemException e) {
                     throw new RuntimeException(e);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
             }

         }


}
