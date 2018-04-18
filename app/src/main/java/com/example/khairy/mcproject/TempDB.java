package com.example.khairy.mcproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by khairy on 08/12/2017.
 */

public class TempDB extends SQLiteOpenHelper {
    private static String DBName = "TempDB";
    SQLiteDatabase sqlDB;

    public TempDB(Context context) {
        super(context,DBName,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cart(name text primary key,img integer not null,count integer not null,cost text not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist cart");
        onCreate(db);
    }
    public void addItem(int img, String name, int count, String cost) {
        ContentValues row = new ContentValues();
        row.put("img",img);
        row.put("name",name);
        row.put("count",count);
        row.put("cost",cost);
        sqlDB = getWritableDatabase();
        sqlDB.insert("cart",null,row);
        sqlDB.close();
    }
    public Integer ItemExist(String name){
        sqlDB = getReadableDatabase();
        String[] rows = {name};
        Cursor cursor = sqlDB.rawQuery("select name,count from cart where name like ?",rows);

        if (cursor.getCount() == 0)
            return 0;
        cursor.moveToFirst();
        return cursor.getInt(1);
    }
    public void deleteItem(String name){
        sqlDB = getWritableDatabase();
        sqlDB.delete("cart","name = '"+name+"'",null);
        sqlDB.close();
    }
    public void updateCount(String name,int count){
        sqlDB = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("count",count);
        sqlDB.update("cart",row,"name like ?",new String[]{name});
        sqlDB.close();
    }
    public Cursor getAllProducts(){

        sqlDB = getReadableDatabase();
        String[] rows = {"img","name","count","cost"};
        Cursor cursor = sqlDB.query("cart",rows,null,null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        sqlDB.close();
        return cursor;

    }
}
