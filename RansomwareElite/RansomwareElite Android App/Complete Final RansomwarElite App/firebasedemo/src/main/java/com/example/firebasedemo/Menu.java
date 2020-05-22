package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//PERMISSION VERIFICATION MODULE CODE
//3 BUTTONS
//Opens after logging-In
public class Menu extends AppCompatActivity {

    Button btndash;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);

        btndash=(Button)findViewById(R.id.btndash_menu);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    //startActivity(new Intent(Dashboard.this, MainActivity.class));
                    startActivity(new Intent(Menu.this, WelcomeActivity.class));
                    finish();
                }
            }
        };

        btndash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(in);
                finish();
            }
        });
    }

    public void go(View v)
    {
        if(v.getId()==R.id.b3) {
            Intent in3 = new Intent(this, MainActivity1.class);
            this.startActivity(in3);
            finish();
        }
        else
        {
            Intent in3 = new Intent(this, Third.class);
            this.startActivity(in3);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(Dashboard.this, auth.getUid(), Toast.LENGTH_SHORT).show();
        auth.addAuthStateListener(authListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
