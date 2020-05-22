package com.example.firebasedemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
Button btn1;
EditText edt1;
String email;
private FirebaseAuth auth;
private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forgot_password);
        initviews();
    }

    private void initviews() {
        btn1=(Button)findViewById(R.id.btn1forgotpass);
        edt1=(EditText) findViewById(R.id.edt1forgotpass);
        auth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        email=edt1.getText().toString().trim();
        if(email.isEmpty())
        {
            edt1.setError("Enter Your Email-ID");
            edt1.requestFocus();
            return;
        }

        pd.setMessage("Sending Email..");
        pd.show();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!.You need to sign-in again with your new password", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(in);
                            finish();
                        } else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "You aren't Entering a Valid Email-Id!", Toast.LENGTH_SHORT).show();
                        }else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                            pd.dismiss();
                            Toast.makeText(ForgotPassword.this, "You aren't a Registered User!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return;
    }
}
