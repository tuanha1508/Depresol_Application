package com.example.depresol;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent data from Register.class activity
//        mobile = getIntent().getStringExtra("mobile");
//        email = getIntent().getStringExtra("email");
//        name = getIntent().getStringExtra("name");
    }
}
