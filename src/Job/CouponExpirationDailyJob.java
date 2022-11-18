package Job;
import DAO.CouponsDAO;
import DAOImplementation.CouponsDBDAO;
import Exceptions.CouponSystemException;


public class CouponExpirationDailyJob extends Thread {

    CouponsDAO couponsDAO = new CouponsDBDAO();
    private Thread thread = new Thread(this,"daily job");

    private boolean quit = true;



    @Override
    public void run() {

        while (quit) {
            try {
                couponsDAO.deleteExpiredCoupons();
                Thread.sleep(43_200_000); // 12 hours
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
                break;
            } catch (CouponSystemException e) {
                System.out.println("Thread error , please check thread again !");
            }
        }
        System.out.println("job stoped");
    }

    public void stopJob(){
        quit = false;
        Thread.interrupted();

    }

    public void startJob(){
        thread.start();
        System.out.println("job started");
    }

    public Thread getThread() {
        return thread;
    }
}
