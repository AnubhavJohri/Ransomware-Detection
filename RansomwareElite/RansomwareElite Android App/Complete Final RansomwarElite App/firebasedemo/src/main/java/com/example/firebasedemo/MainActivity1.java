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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {

    EditText t;
    PackageManager pm;
    ArrayList<String> a = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main1);
        t = (EditText) findViewById(R.id.text);

        PackageManager pm = getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for(ApplicationInfo p : packages)
        {
            a.add(String.valueOf(p.packageName));
        }


        final ListView lv=(ListView)findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitems, R.id.t1, a);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=(String)adapterView.getItemAtPosition(i);
                t.setText(item);
            }
        });

    }

    int c=0;
    private boolean valid() {
        String name = t.getText().toString();

        if (t.getText().toString().equals("")) {
            t.setError("please enter package name");
            t.requestFocus();
            return false;
        }

        for (int i = 0; i < a.size(); i++) {
            if (name.equals(a.get(i))) {
                c = 1;
            }
        }

        if (c == 0)
        {
            t.setError("You have entered a wrong package name");
            t.requestFocus();
            return false;
        }

        else if (t.getText().toString().equals("Enter Package Name")) {
            t.setError("please enter package name");
            t.requestFocus();
            return false;
        }else{
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(t.getText().toString(), PackageManager.GET_PERMISSIONS);
                if (packageInfo == null) {
                    t.setError("you have entered wrong package name");
                    t.requestFocus();
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return true;
            }
        }



    public void show(View v)
    {
        if(valid()) {

            Intent in=new Intent(this,Second.class);

            int g=0;
            int f=0;

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
                    "android.permission.READ_HISTORY_BOOKMARKS",
            };

            String[] s2 = { "android.permission.BIND_DEVICE_ADMIN"
            };

            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(t.getText().toString(), PackageManager.GET_PERMISSIONS);
                //Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        for (int j = 0; j < 15; j++) {
                            if (requestedPermissions[i].equals(s[j])) {
                                g++;
                            }
                        }
                    }

                    for (int i = 0; i < requestedPermissions.length; i++) {
                        for (int j = 0; j < 1; j++) {
                            if (requestedPermissions[i].equals(s2[j])) {
                                f++;
                            }
                        }
                    }

                    if (g>13 || f>0) {
                        in.putExtra("item", "Yes This Application is Suspicious" );

                    } else {
                        in.putExtra("item","No This Application is not Suspicious");
                    }

                    startActivity(in);

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}


