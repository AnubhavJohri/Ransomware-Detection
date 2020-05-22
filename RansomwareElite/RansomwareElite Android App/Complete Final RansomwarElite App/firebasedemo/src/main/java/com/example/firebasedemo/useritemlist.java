package com.example.firebasedemo;

/**
 * Created by hp on 3/6/2018.
 */
//Getter Setter Class used in UsersDisplay.java

public class useritemlist {
    private  String name;
    private  String mobile;
    private  String gender;

    public useritemlist(String name, String mobile, String gender)
    {
        this.name=name;
        this.mobile=mobile;
        this.gender=gender;
    }


    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }
}
