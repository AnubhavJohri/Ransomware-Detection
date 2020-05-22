package com.example.firebasedemo;
//Dashboard of the user
//Should be a Navigation Bar Activity Instead of Empty Activity
//has:-
//1.)Update Password/Email-Id
//2.)Sign-Out
//3.)Delete Account
//4.)Update Information
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;


public class Dashboard extends AppCompatActivity  {

    Button btnchangeemail,btnchangepass,btnchange,btnsignout,btnremove,btnupdate;
    EditText edtoldpass,edtnewpass,edtoldmail,edtnewmail;
    String btnoldpass,btnnewpass,btnoldmail,btnnewmail;
    TextView tv1;
    ProgressDialog pd;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private int flag=0;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        initviews();

        getTimeFromAndroid();//Text View Good Morning/Afternoon/Night Gestures at the top of the activity

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
                    startActivity(new Intent(Dashboard.this, MainActivity.class));
                    finish();
                }
            }
        };

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),UpdateInfo.class);
                startActivity(in);
            }
        });

        btnchangeemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                btnchange.setVisibility(View.VISIBLE);
                edtnewmail.setVisibility(View.VISIBLE);
                edtoldmail.setVisibility(View.GONE);
                edtnewpass.setVisibility(View.GONE);
                edtoldpass.setVisibility(View.GONE);

                btnchangeemail.setVisibility(View.GONE);
                btnsignout.setVisibility(View.GONE);
                btnremove.setVisibility(View.GONE);
                btnchangepass.setVisibility(View.GONE);
            }
        });


        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=2;
                btnchange.setVisibility(View.VISIBLE);
                edtnewmail.setVisibility(View.GONE);
                edtoldmail.setVisibility(View.GONE);
                edtnewpass.setVisibility(View.VISIBLE);
                edtoldpass.setVisibility(View.GONE);
                btnchangeemail.setVisibility(View.GONE);
                btnsignout.setVisibility(View.GONE);
                btnremove.setVisibility(View.GONE);
                btnchangepass.setVisibility(View.GONE);
            }
        });

        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        btnremove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (user != null)
                {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Your profile is deleted, Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Register.class));
                                        finish();
                                        //       progressBar.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                        //     progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Updating Credentials..");
                pd.show();
                if (flag == 1) {
                    if (user != null && !edtnewmail.getText().toString().trim().equals("")) {
                        user.updateEmail(edtnewmail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pd.dismiss();
                                            Toast.makeText(Dashboard.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                            signOut();
                                            finish();
                                            //progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(Dashboard.this, "Failed to update email!"+task.getException(), Toast.LENGTH_LONG).show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    } else if (edtnewmail.getText().toString().trim().equals("")) {
                        edtnewmail.setError("Enter email");
                        edtnewmail.requestFocus();
                        //progressBar.setVisibility(View.GONE);
                    }
                }
                else if(flag==2){
                    if (user != null && !edtnewpass.getText().toString().trim().equals("")) {
                        if (edtnewpass.getText().toString().trim().length() < 6) {
                            edtnewpass.setError("Password too short, enter minimum 6 characters");
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            user.updatePassword(edtnewpass.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                pd.dismiss();
                                                Toast.makeText(getApplicationContext(), "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                                signOut();
                                                finish();
                                                //progressBar.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to update password!"+task.getException(), Toast.LENGTH_SHORT).show();
                                                //progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                    } else if (edtnewpass.getText().toString().trim().equals("")) {
                        edtnewpass.setError("Enter password");
                        edtnewpass.requestFocus();
                        //progressBar.setVisibility(View.GONE);
                    }
                }

            }
            });
    }

    public void signOut() {
        auth.signOut();
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


    private void initviews() {
        btnchangeemail=(Button)findViewById(R.id.change_emaildash);
        btnchangepass=(Button)findViewById(R.id.change_passdash);
        btnchange=(Button)findViewById(R.id.change);
        btnremove=(Button)findViewById(R.id.remove);
        btnsignout=(Button)findViewById(R.id.Signout);
        btnupdate=(Button)findViewById(R.id.updatebtndash);
        tv1=(TextView)findViewById(R.id.tv1dash);


        edtoldpass=(EditText) findViewById(R.id.old_passdash);
        edtnewpass=(EditText)findViewById(R.id.new_passdash);
        edtoldmail=(EditText)findViewById(R.id.old_maildash);
        edtnewmail=(EditText)findViewById(R.id.new_maildash);

        btnchange.setVisibility(View.GONE);
        edtnewmail.setVisibility(View.GONE);
        edtoldmail.setVisibility(View.GONE);
        edtnewpass.setVisibility(View.GONE);
        edtoldpass.setVisibility(View.GONE);
    }

    private void getTimeFromAndroid() {
        Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();
        if(hours>=1 && hours<=12){
        tv1.setText("Good Morning!");
        }else if(hours>=12 && hours<=16){
            tv1.setText("Good AfterNoon!");
        }else if(hours>=16 && hours<=24){
            tv1.setText("Good Evening!");
        }
        }
    }


