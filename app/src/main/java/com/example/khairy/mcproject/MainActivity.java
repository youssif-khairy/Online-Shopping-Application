package com.example.khairy.mcproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final CheckBox remmbr = (CheckBox)findViewById(R.id.remember);
        getApplicationContext().deleteDatabase("TempDB");
        final lastVisitedUser saveUser = new lastVisitedUser(this);

        final AutoCompleteTextView username = (AutoCompleteTextView)findViewById(R.id.user_name);
        final AutoCompleteTextView pass = (AutoCompleteTextView)findViewById(R.id.password);
        final MainDatabase db = new MainDatabase(this);

        //show saved users
        Cursor cursor = saveUser.getAllSavedUsers();
        final ArrayAdapter<String> adapteruname =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        final ArrayList<String>unameList = new ArrayList<String>();
        final ArrayList<String>passList = new ArrayList<String>();
        if (cursor != null){
            while (!cursor.isAfterLast()) {
                adapteruname.add(cursor.getString(0));
                unameList.add(cursor.getString(0));
                passList.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        username.setAdapter(adapteruname);


        username.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String x = (String)parent.getItemAtPosition(position);
                int z = unameList.indexOf(x);
                String pp = passList.get(z);

                pass.setText("");
                pass.setText(pp);

            }
        });
        //end show saved users

        Button signup = (Button)findViewById(R.id.sign_up);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent su = new Intent(MainActivity.this,SignUp.class);
                startActivity(su);
            }
        });



        Button signn = (Button)findViewById(R.id.sign_in);


        signn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = db.searchSignin(username.getText().toString(),pass.getText().toString());
                if (!check)
                    Toast.makeText(getApplicationContext(),"You Are Not Signed IN",Toast.LENGTH_LONG).show();
                //fl if 7ot lw howa already fl database el ra2ysya bs dyfo
                if (remmbr.isChecked() && check) { // save last user
                    if (!saveUser.checkUserExist(username.getText().toString())) {
                        saveUser.addUser(username.getText().toString(), pass.getText().toString());
                        adapteruname.add(username.getText().toString());
                        unameList.add(username.getText().toString());
                        passList.add(pass.getText().toString());
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                }

                if (check) {

                    Intent i = new Intent(MainActivity.this,Categories.class);
                    Global.username = username.getText().toString();
                    startActivity(i);
                }

            }
        });





        Button forget = (Button)findViewById(R.id.forget);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forg = new Intent(MainActivity.this,ForgetPassword.class);
                startActivity(forg);
            }
        });


    }

}
