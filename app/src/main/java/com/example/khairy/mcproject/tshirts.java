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
import java.util.List;

public class tshirts extends AppCompatActivity {
    public TempDB Tempdb = new TempDB(this);
    MainDatabase db = new MainDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirts);

        tshirts.Myadapter_tshirt Adapter = new tshirts.Myadapter_tshirt(this);

        ListView ls = (ListView)findViewById(R.id.my_tshirts);

        ls.setAdapter(Adapter);

        Button mc = (Button)findViewById(R.id.tshirts_my_cart);
        mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tshirts.this,MyCart.class);
                startActivity(i);
            }
        });

        Button lout = (Button)findViewById(R.id.tshirts_logout);
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tshirts.this,MainActivity.class);
                startActivity(i);
            }
        });

    }



    class Myadapter_tshirt extends BaseAdapter {

        ArrayList<tshirt> Items2=new ArrayList<tshirt>();

        Context context;

        Myadapter_tshirt(Context c){
            context = c;
            Items2.add(new tshirt(R.drawable.t1,"Adidas","250$"));
            Items2.add(new tshirt(R.drawable.t2,"Kids","120$"));
            Items2.add(new tshirt(R.drawable.t3,"Simple","200$"));

            db.addProduct(4,"Adidas",250,100,2);
            db.addProduct(5,"Kids",120,100,2);
            db.addProduct(6,"Simple",200,100,2);

        }


        @Override
        public int getCount() {
            return Items2.size();
        }

        @Override
        public Object getItem(int position) {
            return Items2.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//instead of getLayoutInflater()
            View row = inflater.inflate(R.layout.tshirts_list,parent,false);
            final TextView ttl = (TextView) row.findViewById(R.id.tshirt_title);
            final ImageView img = (ImageView) row.findViewById(R.id.tshirt_iv);
            final TextView cst = (TextView)row.findViewById(R.id.tshirt_cost);
            final EditText cnt = (EditText) row.findViewById(R.id.tshirt_count);
            final tshirt item = Items2.get(position);
            ttl.setText(item.name);
            img.setImageResource(item.img);
            cst.setText(item.cost);
            Button ad2crt = (Button)row.findViewById(R.id.add_to_cart_tshirt);

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
class tshirt {
    int img;
    String name;
    String cost;


    tshirt(int i,String n,String coast) {

        img=i;
        name=n;
        cost = coast;
    }
}

