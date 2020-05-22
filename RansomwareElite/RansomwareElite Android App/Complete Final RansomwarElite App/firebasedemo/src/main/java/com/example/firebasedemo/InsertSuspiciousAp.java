package com.example.firebasedemo;

//Non-Activity File used by Third.java to insert JSON Object containing APPname and App condition to the Firebase
//Has no Work ,simply a structure
/**
 * Created by hp on 3/20/2018.
 */

public class InsertSuspiciousAp {
    String condition;
    String appname;

    public InsertSuspiciousAp(){

    }

    public InsertSuspiciousAp(String condition, String appname) {
        this.appname=appname;
        this.condition=condition;
    }


}
