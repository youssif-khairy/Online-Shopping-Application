package com.example.khairy.mcproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;
import java.util.Date;

/**
 * Created by khairy on 05/12/2017.
 */

public class MainDatabase extends SQLiteOpenHelper {

    private static String DBName = "MainDatabase";
    SQLiteDatabase sqlDB;

    public MainDatabase(Context context) {
        super(context,DBName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customers(username text primary key ,name text not null,password text not null,gender text not null,birthday text not null,job text not null)");
        db.execSQL("create table orders(order_id integer primary key autoincrement,order_date text not null,order_address text not null,uname text not null,FOREIGN KEY(uname) REFERENCES customers(username))");
       db.execSQL("create table categories(categ_id integer primary key,categ_name text not null )");
        db.execSQL("create table products(prod_id integer primary key,prod_name text not null,prod_price integer not null,prod_quantity integer not null,cat_id integer not null ,FOREIGN KEY(cat_id) REFERENCES categories(categ_id))");
        db.execSQL("create table orderdetails(ord_id integer,pro_id integer ,quantity integer not null,FOREIGN KEY(ord_id) REFERENCES orders(order_id)" +
              ",FOREIGN KEY(pro_id) REFERENCES products(prod_id),primary key(ord_id,pro_id))");
////
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if EXISTS customers");
        db.execSQL("drop table if EXISTS orders");
        db.execSQL("drop table if EXISTS categories");
        db.execSQL("drop table if EXISTS products");
        db.execSQL("drop table if EXISTS orderdetails");

        onCreate(db);
    }

    public Cursor namesDB(){
       Cursor cursor =  sqlDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        return cursor;
    }
    public void addCustomer(String uname, String name, String passwordy, String gender, String birthdate , String job) {
        ContentValues row = new ContentValues();
        row.put("username",uname);
        row.put("name",name);
        row.put("password",passwordy);
        row.put("gender",gender);
        row.put("birthday",birthdate);
        row.put("job",job);
        sqlDB = getWritableDatabase();
        sqlDB.insert("customers",null,row);
        sqlDB.close();
    }
    public  boolean UserNameExist(String uname){
        sqlDB = getReadableDatabase();
        String[] rows = {uname};
        Cursor cursor = sqlDB.rawQuery("select username from customers where username like ?",rows);

        if (cursor.getCount() == 0)
            return false;
        cursor.moveToFirst();
        sqlDB.close();
        return true;
    }
    public  boolean searchSignin(String uname,String pass){
        sqlDB = getReadableDatabase();
        String[] rows = {uname,pass};
        Cursor cursor = sqlDB.rawQuery("select username,password from customers where username like ? and password like ?",rows);

        if (cursor.getCount() == 0)
            return false;
        cursor.moveToFirst();
        sqlDB.close();
        return true;
    }
    public  boolean Forget(String uname,String name,String job,String bd){
        sqlDB = getReadableDatabase();
        String[] rows = {uname,name,job,bd};
        Cursor cursor = sqlDB.rawQuery("select * from customers where username like ? and name like ? and job like ? and birthday like ?",rows);

        if (cursor.getCount() == 0)
            return false;
        cursor.moveToFirst();
        sqlDB.close();
        return true;
    }


    //cat
    public void addCat(int id,String name) {
        ContentValues row = new ContentValues();
        row.put("categ_id",id);
        row.put("categ_name",name);
        sqlDB = getWritableDatabase();
        sqlDB.insert("categories",null,row);

    }
    public int getCatID(String name) {
        sqlDB = getReadableDatabase();
        String[] rows = {name};
        Cursor cursor = sqlDB.rawQuery("select categ_id from  categories where categ_name like ?",rows);
        return cursor.getInt(0);
    }
    public Cursor getAllCateg() {
        sqlDB = getReadableDatabase();
        String[] rows = {"categ_id","categ_name"};
        Cursor cursor = sqlDB.query("categories",rows,null,null,null,null,null);
        if (cursor !=null)
            cursor.moveToFirst();
        return cursor;
    }


    //products
    public void addProduct(int id,String name,int price,int quantity,int cat_id) {
        ContentValues row = new ContentValues();
        row.put("prod_id",id);
        row.put("prod_name",name);
        row.put("prod_price",price);
        row.put("prod_quantity",quantity);
        row.put("cat_id",cat_id);
        sqlDB = getWritableDatabase();
        sqlDB.insert("products",null,row);
    }
    public int getProdID(String name) {
        sqlDB = getReadableDatabase();
        String[] rows = {name};
        Cursor cursor = sqlDB.rawQuery("select prod_id from  products where prod_name like ?",rows);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor.getInt(0);
    }
    public Cursor getAllProd() {
        sqlDB = getReadableDatabase();
        String[] rows = {"prod_id","prod_name"};
        Cursor cursor = sqlDB.query("products",rows,null,null,null,null,null);
        if (cursor !=null)
            cursor.moveToFirst();
        return cursor;
    }
    //orders

    public int addOrder(String date,String addr,String uname) {
        ContentValues row = new ContentValues();
        row.put("order_date",date);
        row.put("order_address",addr);
        row.put("uname",uname);
        sqlDB = getWritableDatabase();
        long id= sqlDB.insert("orders",null,row);
        return (int)id;
    }


    //order details
    public int addOrder_details(int oid,int pid, int quantity) {
        ContentValues row = new ContentValues();
        row.put("ord_id",oid);
        row.put("pro_id",pid);
        row.put("quantity ",quantity);
        sqlDB = getWritableDatabase();
        long id= sqlDB.insert("orderdetails",null,row);
        return (int)id;
    }



}
