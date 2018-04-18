package com.example.khairy.mcproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by khairy on 03/12/2017.
 */

public class lastVisitedUser extends SQLiteOpenHelper{

    private static String DBName = "remember";
    SQLiteDatabase sqlDB;

    public lastVisitedUser(Context context) {
        super(context,DBName,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table remember(username text primary key,password text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist remember");
        onCreate(db);
    }
    public void addUser(String uname,String password) {
        ContentValues row = new ContentValues();
        row.put("username",uname);
        row.put("password",password);
        sqlDB = getWritableDatabase();
        sqlDB.insert("remember",null,row);
        sqlDB.close();
    }
    public boolean checkUserExist(String Username) {
        sqlDB = getReadableDatabase();
        String[] rows = {Username};
        Cursor cursor = sqlDB.rawQuery("Select username from remember where username like ?",rows);
        if (cursor.getCount() == 0)
            return false;
        return true;
    }
    public Cursor getAllSavedUsers() {
        sqlDB = getReadableDatabase();
        String[] rows = {"username","password"};
         Cursor cursor = sqlDB.query("remember",rows,null,null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        sqlDB.close();
        return cursor;
    }


}
