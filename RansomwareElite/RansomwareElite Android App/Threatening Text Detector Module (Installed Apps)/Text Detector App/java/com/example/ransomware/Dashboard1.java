package com.example.ransomware;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


//MINOR-1 APP(SENDING RAW FOLDER TEXT FILE TO LOCAL SERVER .i.e. myeclipse server AND RECIEVING RESPONSE(Threat/Non-Threat))
//Feature:-Used Volley Timeout Delay

public class Dashboard1 extends AppCompatActivity {
TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);
        tv1=(TextView)findViewById(R.id.dash1tv1);
        tv2=(TextView)findViewById(R.id.dash1tv2);
        tv3=(TextView)findViewById(R.id.dash1tv3);
        Intent in=getIntent();
        String st1=in.getStringExtra("a");
        tv1.setText("Welcome "+st1);

        rawextract();

       // uploadfile();
    }


    public void rawextract() // FUNCTION TO EXTRACT TEXT FILE FROM RAW FOLDER
    {
        String data="";
        StringBuffer sbuffer=new StringBuffer();
        InputStream is=this.getResources().openRawResource(R.raw.alpha);
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));

        if(is!=null)
        {
            try
            {
                while((data=reader.readLine())!=null)
                {
                    sbuffer.append(data+"\n");
                }
                tv2.setText(sbuffer);
                sendToServer(sbuffer);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

private void sendToServer(final StringBuffer sb)//Used to send text file to myeclipse server using volley not FTP
{
    StringRequest sr=new StringRequest(Request.Method.POST,"http://192.168.43.167:9898/Deep/index.jsp", new Response.Listener<String>() {
        @Override
        public void onResponse(final String s) {
            Toast.makeText(Dashboard1.this, "Success", Toast.LENGTH_SHORT).show();
            //tv2.setText(s);
            tv3.setText(s);
            //tv1.setText(s);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(Dashboard1.this, ""+volleyError, Toast.LENGTH_SHORT).show();
        }
    }
    ){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String,String> h=new HashMap();
            h.put("data",sb.toString());
            return h;
        }
    };
    //RequestQueue mRequestQueue = Volley.newRequestQueue(this).add(sr);//DefaultRetryPolicy.DEFAULT_MAX_RETRIES//DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    RequestQueue mRequestQueue = Volley.newRequestQueue(this);
    int socketTimeout = 12000;//12 seconds - change to what you want
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,1,1);
    sr.setRetryPolicy(policy);
    mRequestQueue.add(sr);
   //Above 5 lines are used to add 12 seconds delay in volley.timeout so that it waits for the response for 12 seconds and avoids volley.timeouterror before that
}



}
