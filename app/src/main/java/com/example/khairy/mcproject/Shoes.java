package com.example.khairy.mcproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Shoes extends AppCompatActivity {
    public TempDB Tempdb = new TempDB(this);
    MainDatabase db = new MainDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes);
        Shoes.Myadapter_shoes Adapter = new Shoes.Myadapter_shoes(this);

        ListView ls = (ListView)findViewById(R.id.my_shoeses);

        ls.setAdapter(Adapter);
        Button mc = (Button)findViewById(R.id.shoes_my_cart);

        mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Shoes.this,MyCart.class);
                startActivity(i);
            }
        });

        Button lout = (Button)findViewById(R.id.shoes_logout);
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Shoes.this,MainActivity.class);
                startActivity(i);
            }
        });


    }



    class Myadapter_shoes extends BaseAdapter {

        ArrayList<Shoeses> Items1=new ArrayList<Shoeses>();

        Context context;

        Myadapter_shoes(Context c){
            context = c;
            Items1.add(new Shoeses(R.drawable.s1,"Nike","40$"));
            Items1.add(new Shoeses(R.drawable.s2,"Spedrine","100$"));
            Items1.add(new Shoeses(R.drawable.s3,"Sport","300$"));

            db.addProduct(1,"Nike",40,100,1);
            db.addProduct(2,"Spedrine",10,1000,1);
            db.addProduct(3,"Sport",300,100,1);


        }


        @Override
        public int getCount() {
            return Items1.size();
        }

        @Override
        public Object getItem(int position) {
            return Items1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//instead of getLayoutInflater()
            View row = inflater.inflate(R.layout.shoes_list,parent,false);
            final TextView ttl = (TextView) row.findViewById(R.id.shoes_title);
            ImageView img = (ImageView) row.findViewById(R.id.shoeses_iv);
            final TextView cst = (TextView)row.findViewById(R.id.shoes_cost);
            final EditText cnt = (EditText)row.findViewById(R.id.shoes_count);
            final Shoeses item = Items1.get(position);
            ttl.setText(item.name);
            img.setImageResource(item.img);
            cst.setText(item.cost);
            Button ad2crt = (Button)row.findViewById(R.id.add_to_cart);
            ad2crt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int exst = Tempdb.ItemExist(ttl.getText().toString());
                    if (exst == 0){
                        Tempdb.addItem(item.img,ttl.getText().toString(),Integer.parseInt(cnt.getText().toString()),cst.getText().toString());
                    }
                    else {
                        Tempdb.updateCount(ttl.getText().toString(),exst+Integer.parseInt(cnt.getText().toString()));
                    }
                    Toast.makeText(context.getApplicationContext(),"Added To Cart",Toast.LENGTH_LONG).show();
                }
            });
            return row;
        }
    }




}
class Shoeses {
    int img;
    String name;
    String cost;


    Shoeses(int i,String n,String coast) {

        img=i;
        name=n;
        cost = coast;
    }
}
