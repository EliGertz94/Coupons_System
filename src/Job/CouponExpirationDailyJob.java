package Job;

import Beans.Coupon;
import DAO.CouponsDAO;
import DAOImplementation.CouponsDBDAO;
import Exceptions.CouponSystemException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class CouponExpirationDailyJob extends Thread {

    CouponsDAO couponsDAO = new CouponsDBDAO();

    private boolean quit = true;



    @Override
    public void run() {
        LocalDateTime date = LocalDateTime.now();
        ArrayList<Coupon> coupons = new ArrayList<>();
        while (quit) {
            try {
                coupons.addAll(couponsDAO.getAllCoupons());
                System.out.println("checking");
                for (Coupon coupon : coupons) {
                    if (coupon.getEndDate().compareTo(date) < 0) {
                        couponsDAO.deleteCoupon(coupon.getId());
                    }
                }
                Thread.sleep(43_200_000); // 12 hours
            } catch (InterruptedException e) {
                //add custom
                System.out.println("Thread was interrupted");
            } catch (CouponSystemException e) {
                System.out.println("Thread error , please check thread again !");
            }

        }
    }

    public void stopJob(){
        quit = false;
        Thread.interrupted();
    }
}
