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

    public ClientFacade logIn(String email, String password,ClientType clientType) {

        switch (clientType) {
            case Administrator:
                AdminFacade adminFacade= new AdminFacade();
                if(adminFacade.logIn(email,password)){
                    return adminFacade;
                }

                break;
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                // logIn() will initialize the id of companyFacade instance
                 if(companyFacade.logIn(email,password).getEmail()!=null)
                 {return companyFacade;}


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




