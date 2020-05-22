package com.example.firebasedemo;
//Used in Admin Activity for adding JSON object of menu( 1.)itemname and 2.)itemprice) item in new tree of menu
/**
 * Created by hp on 3/5/2018.
 */

public class InsertItem {
    public String itemname;
    public String itemprice;
    public InsertItem(){

    }
    public InsertItem(String itemname,String itemprice){
        this.itemname=itemname;
        this.itemprice=itemprice;
    }
}
