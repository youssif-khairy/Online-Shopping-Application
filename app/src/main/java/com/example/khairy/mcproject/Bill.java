package com.example.khairy.mcproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Bill extends AppCompatActivity {

    MainDatabase db = new MainDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent mIntent = getIntent();
        int total = mIntent.getIntExtra("total_cost", 0);


        TextView cst = (TextView)findViewById(R.id.ttl_cst);
        cst.setText(String.valueOf(total));
    }

    @Override
    public void onBackPressed() {
    }
}
