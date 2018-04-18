package com.example.khairy.mcproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getApplicationContext().deleteDatabase("TempDB");
        final EditText funame = (EditText)findViewById(R.id.forget_uname);
        final EditText fname = (EditText)findViewById(R.id.forget_name);
        final EditText fjob = (EditText)findViewById(R.id.forget_job);
        final EditText fbdd = (EditText)findViewById(R.id.forget_bdd);
        final EditText fbdm = (EditText)findViewById(R.id.forget_bdm);
        final EditText fbdy = (EditText)findViewById(R.id.forget_bdy);
        final Button rmmbered = (Button)findViewById(R.id.forget_submit);
        final MainDatabase db = new MainDatabase(getApplicationContext());





        rmmbered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.Forget(funame.getText().toString(),fname.getText().toString(),fjob.getText().toString(), fbdd.getText().toString()+"-"+fbdm.getText().toString()+"-"+fbdy.getText().toString()  )){
                    Intent i = new Intent(ForgetPassword.this,Categories.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong Information",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
    }

}
