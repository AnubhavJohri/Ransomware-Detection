package com.example.firebasedemo;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsersDisplay extends AppCompatActivity {
    private static final String URL_DATA = "https://fir-demo-cbf38.firebaseio.com/";
    RecyclerView rv;
    ProgressDialog pd;
    List<Product> list;
    MyAdapter my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_display);

        rv=(RecyclerView)findViewById(R.id.recyclerview);
        pd=new ProgressDialog(this);
        list=new ArrayList<>();
        my=new MyAdapter(UsersDisplay.this,list);
        pd.setMessage("Loading Data...");
        pd.show();
        rv.setAdapter(my);
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference("admin");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(UsersDisplay.this, dataSnapshot.child("menu").getChildren()., Toast.LENGTH_SHORT).show();;
                 Iterator<DataSnapshot> ds=dataSnapshot.child("menu").getChildren().iterator();
                 while(ds.hasNext()){
                    DataSnapshot dd=ds.next();
                    String json=dd.getValue().toString();
                    //Product p=new Product();
                    Gson g=new Gson();
                    Product p=g.fromJson(json,Product.class);
                    list.add(p);
                     pd.dismiss();
                 }
                Toast.makeText(UsersDisplay.this, ""+list, Toast.LENGTH_SHORT).show();
                my.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        rv.setAdapter(my);
        //RecyclerView.LayoutManager m=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);//For swiping horizontal items
        RecyclerView.LayoutManager m=new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(m);


}}
