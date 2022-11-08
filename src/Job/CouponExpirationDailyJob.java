package Job;
import DAO.CouponsDAO;
import DAOImplementation.CouponsDBDAO;
import Exceptions.CouponSystemException;


public class CouponExpirationDailyJob extends Thread {

    CouponsDAO couponsDAO = new CouponsDBDAO();

    private boolean quit = true;



    @Override
    public void run() {

        while (quit) {
            try {
                couponsDAO.deleteExpiredCoupons();
                Thread.sleep(43_200_000); // 12 hours
            } catch (InterruptedException e) {
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
