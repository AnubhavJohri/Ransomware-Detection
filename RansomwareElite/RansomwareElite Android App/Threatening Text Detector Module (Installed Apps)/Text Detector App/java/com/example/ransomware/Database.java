package com.example.ransomware;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.widget.Toast;

/**
 * Created by hp on 9/27/2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Ransomware";
    private static final String TABLE_NAME="Customer";
    private static final int VERSION=1;
    private static  final String UNAME="username";
    private static final String PASS="password";
    private static final String PHONE="phone";
    private static final String NAME="name";
    private static final String CREATE_TABLE=" create table "+TABLE_NAME+" "+" ( "+NAME+" varchar(30), "+PHONE+"  varchar(10), "+UNAME+" varchar(30)  primary key, "+PASS+" varchar(10));";

    SQLiteDatabase sd;

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void open()
    {
        sd=getWritableDatabase();
    }
    public void close()
    {
        sd.close();
    }

    public long insert(User u)
    {
        ContentValues cv=new ContentValues();
        cv.put(NAME,u.getName());
        cv.put(PHONE,u.getPhone());
        cv.put(UNAME,u.getUname());
        cv.put(PASS,u.getPass());
        return sd.insert(TABLE_NAME,null,cv);
    }

    public User login(String uname,String pass)
    {
        User user1;
        user1 = null;
        if((uname.equals("admin"))&&(pass.equals("admin")))
        {
            user1=new User();
            user1.setUname("admin");
            user1.setPass("admin");
        }
        else
        {
            Cursor c = sd.rawQuery("select * from " + TABLE_NAME + " where " + UNAME + " = ? and " + PASS + " = ? ", new String[]{uname, pass});
            if (c.moveToNext())
            {
                user1=new User();
                user1.setName(c.getString(0));
                user1.setPhone(c.getString(1));
                user1.setUname(c.getString(2));
                user1.setPass(c.getString(3));
            }
        }
        return user1;
    }
}
