package com.example.khairy.mcproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyCart extends AppCompatActivity {

    TempDB tempdb = new TempDB(this);
    MainDatabase db = new MainDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        ArrayList<BuyedItem> myList = new ArrayList<BuyedItem>();

        Cursor c = tempdb.getAllProducts();
        while (!c.isAfterLast()) {
            myList.add(new BuyedItem(c.getInt(0), c.getString(1),String.valueOf(c.getInt(2)), c.getString(3)));
            c.moveToNext();
        }

        MyCart.MyCart_Adapter Adapter = new MyCart.MyCart_Adapter(this,myList);
        ListView ls = (ListView)findViewById(R.id.buyed_list);

        ls.setAdapter(Adapter);
        Button bck = (Button)findViewById(R.id.back_to_cat);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyCart.this,Categories.class);
                startActivity(i);
            }
        });

        Button map = (Button)findViewById(R.id.button2);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyCart.this,MapsActivity.class);
                startActivity(i);
            }
        });



        Button confirm = (Button)findViewById(R.id.get_bill);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyCart.this,Bill.class);
                int total = 0;
                Cursor c = tempdb.getAllProducts();
                Calendar calander = Calendar.getInstance();
                String dat =String.valueOf(calander.get(Calendar.DAY_OF_MONTH)) +"-" + String.valueOf(calander.get(Calendar.MONTH) + 1)+"-"+ String.valueOf(calander.get(Calendar.YEAR));
                while (!c.isAfterLast()) {

                    //get total coast
                    String price = "",x = c.getString(3);
                    for (int z=0;z<x.length()-1;z++) price+=x.charAt(z); //to remove $
                    total+= (c.getInt(2) * Integer.parseInt(price)); // count * price

                    //add orders in db order and add in db orderdetails
                    int oid = db.addOrder(dat,Global.Location,Global.username);
                    int pid = db.getProdID(c.getString(1));
                    db.addOrder_details(oid,pid,c.getInt(2));

                    c.moveToNext();
                }
                i.putExtra("total_cost",total);
                startActivity(i);
            }
        });


    }


    class MyCart_Adapter extends BaseAdapter {

        public ArrayList<BuyedItem> Buy_List = new ArrayList<BuyedItem>();
        Context context;
        MyCart_Adapter(Context c,ArrayList<BuyedItem> l) {
            context = c;
         for (int i=0;i<l.size();i++)
            {
            Buy_List.add(new BuyedItem(l.get(i).img,l.get(i).name,l.get(i).count,l.get(i).cost));
            }

        }

        public void AddItem (int i,String n,String cont,String cst) {
            Buy_List.add(new BuyedItem(i,n,cont,cst));
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return Buy_List.size();
        }

        @Override
        public Object getItem(int position) {
            return Buy_List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//instead of getLayoutInflater()
            View row = inflater.inflate(R.layout.mycart_list,parent,false);
            ImageView imgviw = (ImageView)row.findViewById(R.id.buyed_iv);
            final TextView name = (TextView)row.findViewById(R.id.buyed_title);
            final TextView cnt = (TextView) row.findViewById(R.id.buyed_count);
            TextView cst = (TextView)row.findViewById(R.id.buyed_cost);
            BuyedItem b = Buy_List.get(position);
            imgviw.setImageResource(b.img);
            name.setText(b.name);
            cnt.setText(b.count);
            cst.setText(b.cost);
            Button deletem = (Button)row.findViewById(R.id.buyed_delete);

            deletem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Buy_List.remove(position);
                    notifyDataSetChanged();
                    tempdb.deleteItem(name.getText().toString());
                    Toast.makeText(context.getApplicationContext(),"Item Deleted",Toast.LENGTH_LONG).show();
                }
            });

            Button inc = (Button)row.findViewById(R.id.buyed_increase);
            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int c = Integer.parseInt(cnt.getText().toString());
                    c++;
                    cnt.setText(String.valueOf(c));
                    tempdb.updateCount(name.getText().toString(),c);
                }
            });
            Button dec = (Button)row.findViewById(R.id.buyed_decrease);
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int c = Integer.parseInt(cnt.getText().toString());
                    if (c!=1) {
                        c--;
                        cnt.setText(String.valueOf(c));
                        tempdb.updateCount(name.getText().toString(),c);
                    }
                }
            });



            return row;
        }


    }

    @Override
    public void onBackPressed() {
    }


}


class BuyedItem {
    int img;
    String name;
    String count;
    String cost;
    BuyedItem(int i,String n,String cont,String cst) {

        img=i;
        name=n;
        count = cont;
        cost = cst;
    }


}
