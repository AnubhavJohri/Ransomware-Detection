package com.example.ransomware;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "REDIRECTING..",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=getIntent();
                if((i.getStringExtra("a").equals("a")))
                {
                    Intent in=new Intent(Splash.this,Signup.class);
                    startActivity(in);
                }
                else if(i.getStringExtra("a").equals("b"))
                {
                    Intent in=new Intent(Splash.this,MainActivity.class);
                    startActivity(in);
                }
            }
        },2000);
    }
}
