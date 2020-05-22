package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Second extends AppCompatActivity {

    TextView res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        res = (TextView) findViewById(R.id.result);

        Bundle b=getIntent().getExtras();
        String itemValue = b.getString("item");

        res.setText(itemValue);
    }

    public void check(View v)
    {
        if(v.getId()==R.id.b1) {
            Intent in2 = new Intent(this, Menu.class);
            this.startActivity(in2);
            finish();
        }
        else
        {
            finish();
            System.exit(0);
        }
    }
}
