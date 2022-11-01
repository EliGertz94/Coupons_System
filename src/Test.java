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

                 System.out.println("company facade ");
                  CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().logIn("12@gmail.com","1111E006A",ClientType.Company);

                 ArrayList<Coupon> allCompanyCoupons= companyFacade.getAllCompanyCoupons();
                 System.out.println("getAllCompanyCoupons()");
                 for(Coupon coupon1 :allCompanyCoupons){
                     System.out.println(coupon1);
                 }

                 ArrayList<Coupon> CompanyCouponsByCategory= companyFacade.getAllCompanyCoupons(Category.Electricity);
                 System.out.println( " BY CATEGORY "+ CompanyCouponsByCategory);
                 System.out.println(" company details  "+companyFacade.getCompanyDetails());


                 Coupon  coupon =  new Coupon
                         (6, Category.Food, "coupon title", "coupon description", LocalDateTime.now(), LocalDateTime.of(2022,12,12,12,12),
                 100, 200, "String image");


                 // add a coupon
//                 companyFacade.addCoupon(coupon);
//
//                 coupon.setDescription("description of coupon");
//                 coupon.setCategory(Category.Food);
//                 coupon.setId(39);
//                 coupon.setDescription("some other description");
//
//                 //update changes
//                 companyFacade.updateCoupon(coupon);
                 //----delete a coupon by id
                 //will delete purchases as well thanks to - ON DELETE CASCADE SQL
                 // companyFacade.deleteCoupon(30);
                 //----return all the coupons of the company
                 System.out.println("id companyfacade"+ companyFacade.getCompanyId());
                 //   ArrayList<Coupon> companyCouponsByMaxPrice=companyFacade.getAllCompanyCoupons(150);
                 //get logged in company details


                 //----------- customerFacade -----------

                 System.out.println("customerFacade");
                 CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().logIn
                         ("email1@gmail.com","password",ClientType.Customer);
                 //purchaseCoupon
                 customerFacade.purchaseCoupon(35);

                 //get customer coupons
                 ArrayList<Coupon> customersCoupons = customerFacade.getCustomerCoupons();
                 System.out.println("customersCoupons");
                 System.out.println(customersCoupons);
                 //get customer coupons by max price
                 ArrayList<Coupon> customerCouponByMaxPrice = customerFacade.getCustomerCoupons(200);
                 System.out.println("customerCouponByMaxPrice");
                 System.out.println(customerCouponByMaxPrice);
                 //get customer coupons by Category
                 ArrayList<Coupon> customerCouponByCategory = customerFacade.getCustomerCoupons(Category.Food);
                 System.out.println("customerCouponByCategory");
                 System.out.println(customerCouponByCategory);
                 //customer details
                 Customer customer1=  customerFacade.getCustomerDetails();
                 System.out.println(customer1);



             } catch (CouponSystemException e) {
                 System.out.println(e.getMessage());;
             }finally {
                 couponJob.stopJob();
                 try {
                     ConnectionPool.getInstance().closeAllConnections();
                 } catch (CouponSystemException e) {
                     throw new RuntimeException(e);
                 }
             }

         }


}
