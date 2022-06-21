package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity_Nofications extends AppCompatActivity {

    LinearLayout kl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nofications);
        kl = findViewById(R.id.screen_nofications);
        View v = getLayoutInflater().inflate(R.layout.item_nofications, null);
        kl.addView(v);
        View v2 = getLayoutInflater().inflate(R.layout.item_nofications, null);
        //kl.addView(v2);
    }
}