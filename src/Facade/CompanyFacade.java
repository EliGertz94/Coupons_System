package Facade;

import Beans.Coupon;
import Exceptions.CouponSystemException;

public class CompanyFacade extends ClientFacade{
    // יש לבדוק את פרטי ה-Login (אימייל וסיסמה) מול מסד הנתונים.
    @Override
    public boolean logIn(String email, String password) {
       if(companiesDAO.isCompanyExists(email,password)){
           return true;
       }
       return false;
    }

    //הוספת קופון חדש.
    //o אין להוסיף קופון בעל כותרת זהה לקופון קיים של אותה החברה. מותר להוסיף קופון
    //בעל כותרת זהה לקופון של חברה אחרת.


    public void addCoupon(Coupon coupon) throws CouponSystemException {

        if(companiesDAO.checkCompany(coupon.getCompanyId())){
        if(!couponsDAO.uniqueTitleByCompany(coupon.getCompanyId(),coupon.getTitle())){
            couponsDAO.addCoupon(coupon);
        }else {
            System.out.println("this title for a coupon exist already");
        }
        }else{
            System.out.println("no such company ");
        }
    }



}
