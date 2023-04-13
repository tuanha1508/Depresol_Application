package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Result_analysis extends AppCompatActivity {
    String data = "";
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_analysis);
        txt = (TextView) findViewById(R.id.text_result);
        data = getIntent().getStringExtra("data");
        show();
    }
    void show(){
        int begin = data.indexOf("[");
        int end = data.indexOf("]");
        String cleanInput = data.substring(begin+1, end );
        String[] elements = cleanInput.split(",\\s*");
        String postive = elements[0];
        String none = elements[1];
        String negative = elements[2];
        int total = Integer.parseInt(postive) + Integer.parseInt(none) + Integer.parseInt(negative);
        float result = (float)(Integer.parseInt(negative) / total);
        String tmp = "Tổng số câu tin nhắn: " + Integer.toString(total) + '\n' + "Số câu tích cực: " + postive + '\n' + "Số câu tiêu cực: " + negative + "\n" + "Dự đoán: " + Float.toString(result);
        txt.setText(tmp);
    }
}