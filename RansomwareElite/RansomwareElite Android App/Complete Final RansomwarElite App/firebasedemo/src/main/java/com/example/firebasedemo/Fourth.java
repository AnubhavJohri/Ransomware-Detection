package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//PERMISSION VERIFICATION MODULE CODE
public class Fourth extends AppCompatActivity {

    TextView l;
    Button btn;
    String items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        l=(TextView)findViewById(R.id.l);

        btn=(Button)findViewById(R.id.fourthbtn1);
        Bundle b=getIntent().getExtras();
        items=b.getString("items");

        l.setText(items);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Menu.class);
                startActivity(in);
                finish();
            }
        });

    }
}
