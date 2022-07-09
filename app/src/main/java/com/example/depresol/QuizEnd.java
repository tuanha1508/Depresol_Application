package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class QuizEnd extends AppCompatActivity {
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);
        Intent intent = getIntent();
        score = intent.getIntExtra("score" , 0);

        if(score <= 14){

        } else if(14 <= score && score <= 19 ){

        } else if(20 <= score && score <= 29){

        } else{

        }
    }
}