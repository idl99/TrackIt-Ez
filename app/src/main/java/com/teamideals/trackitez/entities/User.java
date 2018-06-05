package com.teamideals.trackitez.entities;

import java.io.Serializable;

public class User implements Serializable {

    /**
     * User Entity class
     */

    private String fName;
    private String lName;
    private String email;

    private User(String fName, String lName, String email) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Method which returns an instance of User
     */
    public static User getInstance(String email, String password){
        if(email.equals("admin@gmail.com") && password.equals("admin")){
            return new User("Ihan","Dilnath","admin@gmail.com");
        } else
            return null; // Invalid credentials
    }

}
