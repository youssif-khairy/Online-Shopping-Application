package com.example.khairy.mcproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class Open_Calender extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open__calender);

        CalendarView cv = (CalendarView)findViewById(R.id.calendarView);

        cv.setDate(2000);
        Button b = (Button)findViewById(R.id.button);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                Global.SignUP_BirthDate = dayOfMonth+"-"+(month+1)+"-"+year;

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Open_Calender.this, SignUp.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }


}
