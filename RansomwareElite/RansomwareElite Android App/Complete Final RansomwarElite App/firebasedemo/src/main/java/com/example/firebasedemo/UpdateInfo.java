package com.example.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;//package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateInfo extends AppCompatActivity {
    AutoCompleteTextView aedt;
    Spinner sp1;
    EditText edt1,edt2;
    Button saveinfobtn;
    RadioGroup rg;
    RadioButton rb;
    public static final String MyPREFERENCES = "MyPrefs";
    //TextView tv;
    String fname,sname,acomp;
    ArrayAdapter<String> adapter;
    private DatabaseReference mDatabaseReference ;//rootreference
    private FirebaseAuth mFirebaseAuth;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_update_info);


        initViews();//Initialises all the edit texts,autocompletetextview etc.
        autocompletetv();
        //Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();//Root Reference
        saveinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });


    }

    private void saveUserInfo() {
        //String name=null,mobile=null,occupation=null,country=null;
        String name=edt1.getText().toString().trim();
        String mobile=edt2.getText().toString().trim();
        String country=sp1.getSelectedItem().toString();
        String occupation=aedt.getText().toString().trim();
        //String gender="male";
        rg=(RadioGroup)findViewById(R.id.rg1main);
        int rbid=rg.getCheckedRadioButtonId();
        rb=(RadioButton)findViewById(rbid);
        String gender=rb.getText().toString();

        /*sharedpreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("namekey",name);
        editor.putString("mobilekey",mobile);
        editor.putString("countrykey",country);
        editor.putString("occupationkey",occupation);
        editor.commit();*/
        //Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
        if(name==null)
        {
            edt1.setError("Can't be left Empty!");
            edt1.requestFocus();
            return;
        }
        else if(mobile==null)
        {
            edt2.setError("Can't be left Empty!");
            edt2.requestFocus();
            return;
        }
        else if(aedt==null)
        {
            edt2.setError("Can't be left Empty!");
            edt2.requestFocus();
            return;
        }
        else if(!(Patterns.PHONE.matcher(mobile).matches())||(mobile.length()<10))
        {
            edt2.setError("Please Enter a Valid Mobile Number!");
            edt2.requestFocus();
           // Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
           // Toast.makeText(this, mobile.length(), Toast.LENGTH_SHORT).show();
            return;

        }
        else if(!name.matches("[A-Z][a-zA-Z]*"))
        {
            edt1.setError("Please Enter a Valid Name!");
            edt1.requestFocus();
            return;

        }
        else if(gender==null)
        {
            rb.setError("Can't Be left empty!");
            return;
        }
        UserInformation mUserInformation=new UserInformation(name,mobile,gender,country,occupation);
        FirebaseUser user=mFirebaseAuth.getCurrentUser();//getting current user signed-in
        mDatabaseReference.child("users").child(user.getUid()).setValue(mUserInformation);

        Toast.makeText(this, "Information Updated!", Toast.LENGTH_SHORT).show();
        Intent in=new Intent(getApplicationContext(),Dashboard.class);
        startActivity(in);
        finish();

    }


    //---------------------------------------------------------------------------------------------------------------------------


    //1.)Intialises all Views
    private void initViews() {
        aedt = (AutoCompleteTextView) findViewById(R.id.autoedt1updateinfo);
        sp1 = (Spinner) findViewById(R.id.spinner);
        edt1=(EditText)findViewById(R.id.edt1updateinfo);
        edt2=(EditText)findViewById(R.id.edt2updateinfo);
        saveinfobtn=(Button)findViewById(R.id.btn1updateinfo);
        sp1=(Spinner)findViewById(R.id.spinner);
        //reset=(Button)findViewById(R.id.btnmain2);

       /* rg=(RadioGroup)findViewById(R.id.rg1main);
        int rbid=rg.getCheckedRadioButtonId();
        rb=(RadioButton)findViewById(rbid);*/


        //acomp=aedt.getText().toString();
        //fname=edt1.getText().toString();
        //sname=edt2.getText().toString();
    }

    //---------------------------------------------------------------------------------------------------------------------------

    //2.)Method Performing all of AutocompleteTextView Tasks
    private void autocompletetv() {
        //Toast.makeText(this, "Hi!", Toast.LENGTH_SHORT).show();
        String ocp[]={"Self-Employed","Business","HouseMaker","Job","Student","Free-Lancer","Engineer","Doctor","Architect"};
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.edtlist_item1,ocp);
        //List_item=.xml file ; edtList_item1=used to manage how each entry in autocomplete must look!
        aedt.setAdapter(adapter);
        aedt.setThreshold(1);
    }

    //----------------------------------------------------------------------------------------------------------------------------


}
