package com.example.firebasedemo;

/**
 * Created by hp on 3/4/2018.
 */
//Used to send all user information to firebase database
    //Used in UpdateInfo.java
public class UserInformation {

    public String name;//should always be public
    public String mobile;
    public String gender;
    public String country;
    public String occupation;

    public UserInformation(){}

    //Phone ph=new Phone();
    public UserInformation(String name,String mobile,String gender,String country,String occupation)
    {
        this.name=name;
        this.mobile=mobile;
        this.gender=gender;
        this.country=country;
        this.occupation=occupation;
    }
}
