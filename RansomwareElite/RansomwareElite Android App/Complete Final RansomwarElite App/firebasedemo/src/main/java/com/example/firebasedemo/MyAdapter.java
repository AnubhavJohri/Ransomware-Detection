package com.example.firebasedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 3/6/2018.
 */
//Used in UsersDisplay.java for making custom Recycler View Task


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    TextView itemname,itemprice;
    private final Context context;
      List<Product>list;
    public MyAdapter(Context context, List<Product> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li=LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.card_view,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product p=list.get(position);
        itemname.setText(p.getItemname());
        itemprice.setText(p.getItemprice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
            itemname=(TextView) view.findViewById(R.id.tvitemname_cardview);
            itemprice=(TextView) view.findViewById(R.id.tvitemprice_cardview);
        }
    }
}
