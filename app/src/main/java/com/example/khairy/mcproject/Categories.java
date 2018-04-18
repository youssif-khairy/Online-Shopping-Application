package com.example.khairy.mcproject;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;



public class Categories extends AppCompatActivity {



    final int REQ_CODE_VOICE_IN = 1;
    TextView search_input;
    Myadapter Adapter;
    ListView ls;
    String[] MyItemsName;
    ArrayList<String> MyItemsNameHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        MyItemsName = new String[]{"shoes","t-shirts"};
        MyItemsNameHelper = new ArrayList<String>(Arrays.asList(MyItemsName));
        Adapter = new Myadapter(getApplication());
        ls = (ListView)findViewById(R.id.category_list);
        ls.setAdapter(Adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView ttl = (TextView) view.findViewById(R.id.title);

                if (ttl.getText().toString() == "shoes") {
                    Intent i = new Intent(Categories.this,Shoes.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(Categories.this,tshirts.class);
                    startActivity(i);
                }
            }
        });
        Button lo = (Button)findViewById(R.id.logout);
        Button mycrt = (Button)findViewById(R.id.my_cart);
        mycrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Categories.this,MyCart.class);
                startActivity(i);
            }
        });
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Categories.this,MainActivity.class);
                startActivity(i);
            }
        });

        search_input = (TextView)findViewById(R.id.search_field);
        Button voic = (Button)findViewById(R.id.voice);
        voic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voicChangeToTxt();
            }
        });

        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){ //Reset list
                    Adapter = new Myadapter(getApplication());
                    ls.setAdapter(Adapter);
                    MyItemsNameHelper = new ArrayList<String>(Arrays.asList(MyItemsName));
                }
                else {
                    String txt = s.toString();

                    for (int j=0;j<MyItemsNameHelper.size();j++) {
                        if (!MyItemsNameHelper.get(j).contains(txt)) {
                           // Toast.makeText(getApplicationContext(),String.valueOf(j) + " "+txt+" ",Toast.LENGTH_LONG).show();
                            Adapter.deleteItem(j);
                            MyItemsNameHelper.remove(j);
                            j--;
                        }

                    }

                   // Adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void voicChangeToTxt() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say SomeThing");
        startActivityForResult(intent, REQ_CODE_VOICE_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_VOICE_IN && resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search_input.setText(result.get(0));
        }
    }


    @Override
    public void onBackPressed() {
    }
}


class CategoriesItems {
    int img;
    String name;
    CategoriesItems(int i,String n) {

        img=i;
        name=n;
    }
}

class Myadapter extends BaseAdapter {

    ArrayList<CategoriesItems> Items=new ArrayList<CategoriesItems>();
    Context context;

    Myadapter(Context c){
        context = c;
        Items.add(new CategoriesItems(R.drawable.c2,"shoes"));
        Items.add(new CategoriesItems(R.drawable.c1,"t-shirts"));
        MainDatabase db = new MainDatabase(c.getApplicationContext());
        db.addCat(1,"shoes");
        db.addCat(2,"t-shirts");

    }

    public void deleteItem (int itemToDelete) {
        Items.remove(itemToDelete);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //for displaying data in listView (3aby data gwa el listView)
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//instead of getLayoutInflater()
        View row = inflater.inflate(R.layout.categorie_list,parent,false);
        TextView ttl = (TextView) row.findViewById(R.id.title);
        ImageView img = (ImageView) row.findViewById(R.id.imageView);
        CategoriesItems item = Items.get(position);
        ttl.setText(item.name);
        img.setImageResource(item.img);

        return row;
    }
}
