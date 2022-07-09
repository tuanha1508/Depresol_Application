package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizEnd extends AppCompatActivity {
    int score;
    TextView txtResult , chitiet;
    ImageView imgResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);
        Intent intent = getIntent();

        txtResult = findViewById(R.id.result_tram_cam);
        chitiet = findViewById(R.id.chitiet);
        imgResult = findViewById(R.id.img_result);

        score = intent.getIntExtra("score" , 0);

        if(score <= 14){
            txtResult.setText("Không có biểu hiện trầm cảm");
            imgResult.setImageResource(R.drawable.mucdo1);
            chitiet.setText("Bạn gần như là một con người vui vẻ , hạnh phúc!. Hãy tiếp tục nhé!");
        } else if(14 <= score && score <= 19 ){
            txtResult.setText("Trầm cảm nhẹ");
            imgResult.setImageResource(R.drawable.mucdo2);
            chitiet.setText("Bạn có một chút giấu hiệu nhẹ của bệnh trầm cảm!.");
        } else if(20 <= score && score <= 29){
            txtResult.setText("Trầm cảm vừa");
            imgResult.setImageResource(R.drawable.mucdo3);
            chitiet.setText("Bạn đang có biểu hiện của bệnh trầm cảm mức độ vừa.");
        } else{
            txtResult.setText("Trầm cảm nặng");
            imgResult.setImageResource(R.drawable.mucdo4);
            chitiet.setText("Bạn có dấu hiệu của bị trầm cảm. Hãy liên lạc để được sự hỗ trợ kịp thời nhé!");
        }
    }
}