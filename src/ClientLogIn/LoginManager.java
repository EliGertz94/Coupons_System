package ClientLogIn;

import Exceptions.CouponSystemException;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {

    static  LoginManager instance=  new LoginManager();
    private LoginManager(){}

    public static LoginManager getInstance(){return instance;}

    public synchronized ClientFacade logIn(String email, String password,ClientType clientType) throws CouponSystemException {

        switch (clientType) {
            case Administrator:
                AdminFacade adminFacade= new AdminFacade();
                if(adminFacade.logIn(email,password)){
                    return adminFacade;
                }

                break;
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
              //  AdminFacade adminFacade1 = new AdminFacade();
                 if(companyFacade.logIn(email,password))
                 {
                    // if(adminFacade1.getOneCompany(companyFacade.getCompanyId()).getEmail()!= null){
                         return companyFacade;
                  //   }
                 }


                break;
            case Customer:
                CustomerFacade customerFacade= new CustomerFacade();
                    if(customerFacade.logIn(email,password).getEmail()!= null){
                        return customerFacade;
                    }
                break;
        }
        return null;
    }
}




