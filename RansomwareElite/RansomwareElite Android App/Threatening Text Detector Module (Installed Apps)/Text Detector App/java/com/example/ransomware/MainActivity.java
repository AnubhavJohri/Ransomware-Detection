package com.example.ransomware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edt1,edt2;
    Button btn;
    TextView tv1;
    //String st1,st2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st1="a";
                Intent in=new Intent(MainActivity.this,Splash.class);
                in.putExtra("a",st1);
                startActivity(in);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db=new Database(MainActivity.this);
                db.open();
                String str1=null,str2=null;
                str1=edt1.getText().toString();
                str2=edt2.getText().toString();
                //Toast.makeText(MainActivity.this, str1+" "+str2, Toast.LENGTH_SHORT).show();
                User u=db.login(str1,str2);
                if(u!=null)
                {
                    Toast.makeText(MainActivity.this, "Logged-In!", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(MainActivity.this,Dashboard1.class);
                    //Toast.makeText(MainActivity.this, u.getName()+u.getPhone()+u.getUname()+u.getPass(), Toast.LENGTH_SHORT).show();
                    in.putExtra("a",u.getName());
                    startActivity(in);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
    }

    private void initViews()
    {
        edt1=(EditText)findViewById(R.id.xloginedt1);
        edt2=(EditText)findViewById(R.id.xloginedt2);
        tv1=(TextView)findViewById(R.id.xlogintv1);
        btn=(Button)findViewById(R.id.xloginbtn);
    }
}
