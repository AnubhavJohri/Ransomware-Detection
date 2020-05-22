package com.example.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity implements View.OnClickListener{
Button btn1;
EditText email,pass;
private ProgressDialog pd;
String st1=null,st2=null;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_regis);
        initviews();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initviews() {
        email=(EditText)findViewById(R.id.edt1register);
        pass=(EditText)findViewById(R.id.edt2register);
        btn1=(Button)findViewById(R.id.btn1register);
        pd=new ProgressDialog(this);
        //st1=email.getText().toString().trim();
        //st2=pass.getText().toString().trim();
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn1register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
       // st1=email.getText().toString();
        st1=email.getText().toString().trim();
        st2=pass.getText().toString().trim();
        if(st1.isEmpty())
        {
            email.setError("Email-Id can't Be left Blank");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(st1).matches()) {
            email.setError("Email-Id isn't Valid!");
            email.requestFocus();
            return;
        }
        if(st2.isEmpty())
        {
            pass.setError("Password can't be left blank");
            pass.requestFocus();
            return;
        }
        if(st2.length()<6)
        {
            pass.setError("Password can't be less that 6 words/digits!");
            pass.requestFocus();
            return;
        }
        pd.setMessage("Registering User...");
        pd.show();
        mAuth.createUserWithEmailAndPassword(st1, st2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(Register.this,UpdateInfo.class);
                            //in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            pd.dismiss();;
                            Toast.makeText(Register.this, "You Are Already Registered!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed."+task.getException(),Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });


    }
}
