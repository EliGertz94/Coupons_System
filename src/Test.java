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
              */

             //the test will stop in case there is an Exception with the input
             try {


                 //-----"Admin Login"-------
                 System.out.println("admin facade");

                 AdminFacade admin = (AdminFacade) LoginManager.getInstance().logIn("admin@admin.com", "admin", ClientType.Administrator);
                // admin.addCompany(new Company("B 22inllk", "B3fdgfdg3sff@gmail.com", "12fdf323456A"));
                // Company company = admin.getOneCompany(6);
//                 company.setEmail("12@gmail.com");
//                 // company.setName("12dw");
//                 //update after changing email can't change when the name is diffrent
//                 admin.updateCompany(company);
//                 //delete company
//                 Company company2 = admin.getOneCompany(3);
               //  admin.deleteCompany(8);
//                 System.out.println(admin.getAllCompanies());
//                 Customer customer = new Customer("Avi", "shmueli", "AS@gmail.com", "AV1234");
//                 admin.addCustomer(customer);
//                 Customer customerAvi = admin.getOneCustomer(7);
//                 customerAvi.setLastName("Shemesh");
//                 admin.updateCustomer(customerAvi);


                // admin.deleteCustomer(1);
//                 System.out.println(admin.getAllCustomers());



                 //-------------- CompanyFacade --------------

                     System.out.println("company facade ");
                     CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().logIn("AA@gmail.com", "123456A", ClientType.Company);
if (companyFacade!=null){
//                     ArrayList<Coupon> allCompanyCoupons = companyFacade.getAllCompanyCoupons();
//
//                     ArrayList<Coupon> CompanyCouponsByCategory = companyFacade.getAllCompanyCoupons(Category.Electricity);
//


                 Coupon coupon = new Coupon
                         (8, Category.Food, "coup2", "coupon description 1", LocalDateTime.now(), LocalDateTime.of(2022, 12, 12, 12, 12),
                                 100, 200, "String image");
                 //  companyFacade.addCoupon(coupon);


    // add a coupon
////---
//                 //update changes
    // companyFacade.updateCoupon(coupon);
    //----delete a coupon by id
    //will delete purchases as well thanks to - ON DELETE CASCADE SQL
    //  companyFacade.deleteCoupon(33);
    //   ----return all the coupons of the company
    System.out.println("id companyfacade" + companyFacade.getCompanyId());
    ArrayList<Coupon> companyCouponsByMaxPrice = companyFacade.getAllCompanyCoupons(150);
    //get logged in company details
    for (Coupon coupon1 : companyCouponsByMaxPrice) {
        System.out.println(coupon1);
        System.out.println("companyCouponsByMaxPrice" + companyCouponsByMaxPrice);
    }

}                 //       ----------- customerFacade -----------
                 CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().logIn
                         ("el@gmail.com", "123", ClientType.Customer);
                 if(customerFacade!=null){
                     System.out.println("customerFacade");
                     //purchaseCoupon
                     //customerFacade.purchaseCoupon(39);

                     customerFacade.purchaseCoupon(40);
                     customerFacade.purchaseCoupon(41);
                     //get customer coupons
                     ArrayList<Coupon> customersCoupons = customerFacade.getCustomerCoupons();
                     System.out.println("customersCoupons");
                     System.out.println(customersCoupons);
                     //get customer coupons by max price
                     ArrayList<Coupon> customerCouponByMaxPrice = customerFacade.getCustomerCoupons(150);
                     System.out.println("customerCouponByMaxPrice");
                     System.out.println(customerCouponByMaxPrice);
                     //get customer coupons by Category
                     ArrayList<Coupon> customerCouponByCategory = customerFacade.getCustomerCoupons(Category.Electricity);
                     System.out.println("customerCouponByCategory");
                     System.out.println(customerCouponByCategory);
                     //customer details
                     Customer customer1 = customerFacade.getCustomerDetails();
                     System.out.println(customer1 + " getCustomerDetails");
                 }

             } catch (CouponSystemException e) {
                 System.out.println(e.getMessage());

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
