package com.example.khairy.mcproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    String  BirthDay;
    DatePickerDialog.OnDateSetListener  dlog;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText username = (EditText)findViewById(R.id.user_name1);
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText password = (EditText)findViewById(R.id.password1);
        final MainDatabase db = new MainDatabase(this);
        final EditText bd = (EditText)findViewById(R.id.BdB);

        final EditText job = (EditText)findViewById(R.id.job);
        Button signup = (Button)findViewById(R.id.sign_up1);
       final RadioButton M = (RadioButton)findViewById(R.id.male);
       final RadioButton F = (RadioButton)findViewById(R.id.female);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radiogroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (M.isChecked()) gender = "male";
                else if (F.isChecked())  gender = "female";
                else gender="";
            }
        });



        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.SignUP_username = username.getText().toString();
                Global.SignUP_name = name.getText().toString();
                Global.SignUP_password = password.getText().toString();
                Global.SignUP_gender = gender;
                Global.SignUP_job = job.getText().toString();
                Intent i = new Intent(SignUp.this,Open_Calender.class);
                startActivity(i);
            }
        });



        if (gender == "male") {M.setChecked(true);F.setChecked(false);}
        else {F.setChecked(true); M.setChecked(false);}

        username.setText(Global.SignUP_username);
        name.setText(Global.SignUP_name);
        password.setText(Global.SignUP_password);
        gender = Global.SignUP_gender;
//        if (gender == "male") M.setChecked(true);
//        else if (gender == "female")F.setChecked(true);
        job.setText(Global.SignUP_job);
        BirthDay = Global.SignUP_BirthDate;
        bd.setText(BirthDay);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add in database
                boolean check =true;
                if (username.getText().toString()=="" ||name.getText().toString()=="" ||password.getText().toString()=="" ||gender =="" ||BirthDay =="" ||job.getText().toString()=="" )
                {Toast.makeText(getApplicationContext(),"Empty Fields",Toast.LENGTH_LONG).show();check = false;}
               else if (username.getText().toString().length()<4 )
                { Toast.makeText(getApplicationContext(),"Username must be greater than 3 charachters",Toast.LENGTH_LONG).show(); check = false;}
                else if ( db.UserNameExist(username.getText().toString()))
                {Toast.makeText(getApplicationContext(),"This username is already Taken",Toast.LENGTH_LONG).show();check = false;}
                else if (password.getText().toString().length() < 6)
                {Toast.makeText(getApplicationContext(),"Password must be greater than 5 charachters",Toast.LENGTH_LONG).show();check = false;}




                if (check) {
                    db.addCustomer(username.getText().toString(), name.getText().toString(), password.getText().toString(), gender, BirthDay, job.getText().toString());
                    Intent i = new Intent(SignUp.this,MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Successful SignUP", Toast.LENGTH_LONG).show();
                    startActivity(i);

                }
            }
        });

    }
    @Override
    public void onBackPressed() {
    }
}
