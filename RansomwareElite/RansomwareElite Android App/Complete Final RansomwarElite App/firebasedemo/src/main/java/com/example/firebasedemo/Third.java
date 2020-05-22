package com.example.firebasedemo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//Activity Has Code of Checking Whole Device for Threatning Application
//Opened When Clicked on Whole Device Button in Menu.java Activity
//Checks 2 Conditions:-1.)Whether <BIND_DEVICE_ADMIN> permission is present in manifest file 2.)Whether >=8 permissions are present in manifest file of every application present in manifest file
public class Third extends AppCompatActivity {

    //CONDITION 1=presence of <BIND_DEVICE_ADMIN> from string s2
    //CONDITION 2=presence of >= 8 Suspicious Permissions from string s
    TextView res;
    StringBuffer sb, sb2;
    String s1;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.third);
        res = (TextView) findViewById(R.id.result);

        Toast.makeText(this, "I am Third!", Toast.LENGTH_SHORT).show();

        String[] s = {"android.permission.RECEIVE_BOOT_COMPLETED",
                "android.permission.READ_PHONE_STATE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WAKE_LOCK",
                "android.permission.GET_ACCOUNTS",
                "android.permission.DISABLE_KEYGUARD",
                "android.permission.INSTALL_SHORTCUT",
                "android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.ACCESS_WIFI_STATE",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.READ_SMS",
                "android.permission.READ_HISTORY_BOOKMARKS",};

        String[] s2 = { "android.permission.BIND_DEVICE_ADMIN",};

        sb=new StringBuffer();
        try {

            int d=0;
            int c=0;
            int e=0;

            PackageManager pm = getPackageManager();

            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo p : packages) {

                c=0;
                e=0;

                PackageInfo packageInfo = getPackageManager().getPackageInfo(p.packageName, PackageManager.GET_PERMISSIONS);

                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        for (int j = 0; j < 15; j++) {
                            if (requestedPermissions[i].equals(s[j])) {
                                c++;
                            }
                        }
                    }


                    for (int i = 0; i < requestedPermissions.length; i++) {
                        for (int j = 0; j < 1; j++) {
                            if (requestedPermissions[i].equals(s2[j])) {
                                e++;
                            }
                        }
                    }


                    if (c>13 || e>0) {
                        String Appname;
                        Appname=p.packageName;
                        d++;
                        sb.append("\n");
                        sb.append(String.valueOf(p.packageName));
                        FirebaseAuth auth=FirebaseAuth.getInstance();
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
                        InsertSuspiciousAp ob = null;
                        if(e>0){
                            ob=new InsertSuspiciousAp("Condition1",Appname);//CONDITION 1=presence of <BIND_DEVICE_ADMIN> from string s2
                        }
                        else if(c>13){
                            ob=new InsertSuspiciousAp("Condition2",Appname);//CONDITION 2=presence of >= 8 Suspicious Permissions from string s
                        }
                        else{
                            ob=new InsertSuspiciousAp("Both",Appname);//CONDITION 3=Both conditions are true
                        }
                        mDatabaseReference.child("Threatning-Apps").child(user.getUid()).push().setValue(ob);

                    }

                }
            }

            s1=sb.toString();

            if(d>0)
            {
                res.setText("Device Unsafe!!" + "\n" + "There Are " + d + " Suspicious Applications In Your Device");
                Toast.makeText(this, "Suspicious Apps Have Been Recorded!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent in3 = new Intent(this, Second.class);
                in3.putExtra("item" ,"Your Device Is Safe" );
                this.startActivity(in3);
            }

            }catch(PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
    }

    public void check(View v)
    {
        if(v.getId()==R.id.b1) {
            Intent in2 = new Intent(this, Menu.class);
            this.startActivity(in2);
            finish();
        }
        else if(v.getId()==R.id.b6)
        {
            Intent in2 = new Intent(this, Fourth.class);
            in2.putExtra("items" , s1);
            this.startActivity(in2);
            finish();
        }
        else
        {
            finish();
            System.exit(0);
        }
    }
}
