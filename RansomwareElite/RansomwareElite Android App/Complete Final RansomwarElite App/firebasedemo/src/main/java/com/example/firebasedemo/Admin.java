package com.example.firebasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity {
Button btn,signoutbtn,UsersDisplaybtn,MenuDisplaybtn;
EditText edtitemname,edtitemprice;
private DatabaseReference mDatabaseReference,mrootrefer;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_admin);
        initViews();
        auth=FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        };//Used To check if the state of user when he closed app was 1.)signed-in or 2.)signed out

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
            }
        });

        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              signOut();
            }
        });

        UsersDisplaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),UsersDisplay.class);
                startActivity(in);
            }
        });

        MenuDisplaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //COMPLETE THIS
                //MAKE A NEW ACTIVITY WHERE WE HAVE TO SHOW THE MENU
                //AND PRODUCE INTENT HERE!
            }
        });
    }

    private void insertItem() {
        String name=edtitemname.getText().toString().trim();
        String price=edtitemprice.getText().toString().trim();
        InsertItem in=new InsertItem(name,price);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child("admin").child("menu").push().setValue(in);
       // mrootrefer.push().setValue(in);
        Toast.makeText(this, "Item Inserted", Toast.LENGTH_SHORT).show();
        edtitemprice.setText("");
        edtitemname.setText("");
    }

    private void initViews() {
        edtitemname=(EditText)findViewById(R.id.edt_itemname_admin);
        edtitemprice=(EditText)findViewById(R.id.edt_itemprice_admin);
        btn=(Button)findViewById(R.id.btn_insertitem_admin);
        signoutbtn=(Button)findViewById(R.id.signout_btn_admin);
        UsersDisplaybtn=(Button)findViewById(R.id.btnUsersDisplay_admin);
        MenuDisplaybtn=(Button)findViewById(R.id.btnMenuDisplay_admin);

    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
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
