package Beans;

import java.util.ArrayList;

public class Customer {

    private int id;
    private String firstName;
    private String lasttName;

    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    public Customer(int id, String firstName, String lasttName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lasttName = lasttName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }


    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lasttName='" + lasttName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
