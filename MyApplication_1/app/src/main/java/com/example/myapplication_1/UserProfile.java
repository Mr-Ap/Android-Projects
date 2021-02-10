package com.example.myapplication_1;

public class UserProfile {

    public String Name,Email,Mobile;

    public UserProfile(String userEmail, String userMobile,String userName) {
        this.Name = userName;
        this.Email = userEmail;
        this.Mobile = userMobile;
    }
}
