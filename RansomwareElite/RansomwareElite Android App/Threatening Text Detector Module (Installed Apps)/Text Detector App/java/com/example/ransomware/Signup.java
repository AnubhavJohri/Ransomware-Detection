package com.example.ransomware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
EditText edt1,edt2,edt3,edt4;
    Button btn;
    TextView tv1;
    //String st1,st2,st3,st4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st1="b";
                Intent in=new Intent(Signup.this,Splash.class);
                in.putExtra("a",st1);
                startActivity(in);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db=new Database(Signup.this);
                User u=new User();
                String st1=null,st2=null,st3=null,st4=null;
                st1=edt1.getText().toString();
                st2=edt2.getText().toString();
                st3=edt3.getText().toString();
                st4=edt4.getText().toString();
               validation();
                u.setName(st1);
                u.setPhone(st3);
                u.setUname(st2);
                u.setPass(st4);
                db.open();
                if(!((st4.length()==0)||(st4.length()<=3)||(st3.length()==0)||(st3.length()<10)||(st2.length()==0)||(!st1.matches("[a-zA-Z ]+")||(st1.length()==0))))
                {
                    if (db.insert(u) > 0) {
                        Toast.makeText(Signup.this, "Successfully Inserted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup.this, "Username Already Exists!", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });
    }

    public void initViews()
    {
        edt1=(EditText)findViewById(R.id.xedt1);//name
        edt2=(EditText)findViewById(R.id.xedt2);//username
        edt3=(EditText)findViewById(R.id.xedt3);//phone
        edt4=(EditText)findViewById(R.id.xedt4);//password
        tv1=(TextView)findViewById(R.id.tv1);
        btn=(Button)findViewById(R.id.xbtn);
    }

    public void validation()
    {
        String st1,st2,st3,st4;
        st1=edt1.getText().toString();//name
        st2=edt2.getText().toString();//username
        st3=edt3.getText().toString();//phone
        st4=edt4.getText().toString();//password
        if(st1.length()==0)
        {
            edt1.requestFocus();
            edt1.setError("FIELD CANNOT BE EMPTY");

        }
        else if(!st1.matches("[a-zA-Z ]+"))
        {
            edt1.requestFocus();
            edt1.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }
        if(st2.length()==0)
        {
            edt2.requestFocus();
            edt2.setError("FIELD CANNOT BE EMPTY");
        }
        if(st3.length()<10)
        {
            edt3.requestFocus();
            edt3.setError("ENTERED MOBILE NUMBER IS NOT A VALID NUMBER!");
        }
        else if(st3.length()==0) {
            edt3.requestFocus();
            edt3.setError("FIELD CANNOT BE EMPTY");
        }
        if(st4.length()<=3)
        {
            edt4.requestFocus();
            edt4.setError("ENTERED ATLEAST 4 CHARACTERS");
        }
        else if(st4.length()==0) {
            edt4.requestFocus();
            edt4.setError("FIELD CANNOT BE EMPTY");
        }
    }
    }
