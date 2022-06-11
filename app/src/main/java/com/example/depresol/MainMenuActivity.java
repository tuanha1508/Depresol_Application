package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenuActivity extends AppCompatActivity {

    String name_user , url_avatar;
    TextView loi_chao;
    int time_now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SimpleDateFormat sdf = new SimpleDateFormat("h");
        String time = sdf.format(new Date());
        time_now = Integer.parseInt(time);
        loi_chao = findViewById(R.id.loi_chao);

        name_user = getIntent().getStringExtra("name_user");
        url_avatar = getIntent().getStringExtra("url_avatar");
        loi_chao.setText(time);
        if(name_user != ""){
            loi_chao.setText("Good");
        }

        if(url_avatar != ""){

        }
    }
}