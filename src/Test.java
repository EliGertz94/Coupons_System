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

                 AdminFacade admin = (AdminFacade) LoginManager.getInstance().logIn("admin@admin.com", "admin", ClientType.Administrator);
               // admin.addCompany(new Company("bbb", "bb@gmail.com", "1fdf323456A"));
               Company company = admin.getOneCompany(1);
                 company.setEmail("1@gmail.com");
                  //company.setName("12dw");
                 //update after changing email can't change when the name is different
               //  admin.updateCompany(company);
                 //delete company
               //  Company company2 = admin.getOneCompany(3);
                 //admin.deleteCompany(4);
               //  System.out.println(admin.getAllCompanies());
                 Customer customer = new Customer("eli", "shmueli", "eli@gmail.com", "AV1234");
             //   admin.addCustomer(customer);
               //  Customer customerAvi = admin.getOneCustomer(7);
              //   customerAvi.setLastName("Shemesh");
              //   admin.updateCustomer(customerAvi);


                // admin.deleteCustomer(1);
//                 System.out.println(admin.getAllCustomers());



                 //-------------- CompanyFacade --------------
//
//                     System.out.println("company facade ");
                     CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().logIn("1@gmail.com", "12fdf323456A", ClientType.Company);


//                     ArrayList<Coupon> allCompanyCoupons = companyFacade.getAllCompanyCoupons();
//
//                     ArrayList<Coupon> CompanyCouponsByCategory = companyFacade.getAllCompanyCoupons(Category.Electricity);
//
//
//
//
//                   companyFacade.addCoupon(new Coupon
//                           (companyFacade.getCompanyId(), Category.Food, "coup1", "coupon description 1", LocalDateTime.now(), LocalDateTime.of(2022, 12, 12, 12, 12),
//                                   100, 200, "String image"));
//                 companyFacade.addCoupon(new Coupon
//                         (companyFacade.getCompanyId(), Category.Food, "coup2", "coupon description 1", LocalDateTime.now(), LocalDateTime.of(2022, 12, 12, 12, 12),
//                                 100, 200, "String image"));
//                 companyFacade.addCoupon(new Coupon
//                         (companyFacade.getCompanyId(), Category.Food, "coup3", "coupon description 1", LocalDateTime.now(), LocalDateTime.of(2022, 12, 12, 12, 12),
//                                 100, 200, "String image"));

                      //companyFacade.deleteCoupon(1);
                 Coupon coupon = new Coupon
                         (companyFacade.getCompanyId(), Category.Electricity, "coup4", "coupon description 1", LocalDateTime.now(),
                                 LocalDateTime.of(2022, 12, 12, 12, 12),
                                 100, 200, "String image");
                 coupon.setId(4);
//                 Coupon coupon = companyFacade.getAllCompanyCoupons().get(0);
//                 coupon.setTitle("blablabla");
//                 coupon.setCategory(Category.Electricity);
//                 System.out.println(coupon);
                 companyFacade.updateCoupon(coupon);
        //   ----return all the coupons of the company
//        ArrayList<Coupon> companyCouponsByMaxPrice = companyFacade.getAllCompanyCoupons(250);
    //    //get logged in company details

    //        System.out.println("companyCouponsByMaxPrice" + companyCouponsByMaxPrice);

//
//                 //       ----------- customerFacade -----------
                 CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().logIn
                         ("eli@gmail.com", "AV1234", ClientType.Customer);

//                     customerFacade.purchaseCoupon(1);
//                     customerFacade.purchaseCoupon(2);
//                     customerFacade.purchaseCoupon(3);

//                     //get customer coupons
//                     ArrayList<Coupon> customersCoupons = customerFacade.getCustomerCoupons();

//                     //get customer coupons by max price
//                     ArrayList<Coupon> customerCouponByMaxPrice = customerFacade.getCustomerCoupons(150);

//                     //get customer coupons by Category
//                     ArrayList<Coupon> customerCouponByCategory = customerFacade.getCustomerCoupons(Category.Electricity);

//                     //customer details
//                     Customer customer1 = customerFacade.getCustomerDetails();
//                 }

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
