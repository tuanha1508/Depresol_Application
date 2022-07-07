package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestions, tvAnswer, tvQuestionNo;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    private Button btnNext;

    String[] ans = new String[10];

    int cnt = 0;
    int totalQuestion;
    int qcounter = 0;

    ColorStateList dfRbcolor;
    boolean answered;

    private QuizModel currentQuestion;
    private List<QuizModel> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        questionList = new ArrayList<>();
        tvQuestions = findViewById(R.id.textQuestion);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);
        btnNext = findViewById(R.id.btnNext);
        dfRbcolor = rb1.getTextColors();
        addQuestions();
        totalQuestion = questionList.size();
        showNextQuestion();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered) {
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(QuizActivity.this, "Vui lòng quyết định lựa chọn của bạn", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if(answerNo == currentQuestion.getCorrectAnsNo()) {
            cnt++;
            if(rb1.isChecked()) {
                ans[cnt] = currentQuestion.getOption1();
            }
            if(rb2.isChecked()) {
                ans[cnt] = currentQuestion.getOption2();
            }
            if(rb3.isChecked()) {
                ans[cnt] = currentQuestion.getOption3();
            }
            if(rb4.isChecked()) {
                ans[cnt] = currentQuestion.getOption4();
            }
            if(rb5.isChecked()) {
                ans[cnt] = currentQuestion.getOption5();
            }
            if(rb6.isChecked()) {
                ans[cnt] = currentQuestion.getOption6();
            }
        }
        if(qcounter < totalQuestion) {
            btnNext.setText("Câu tiếp theo");

        }
        else {
            btnNext.setText("Hoàn thành bài test :>");
        }
    }

    private void showNextQuestion() {
        radioGroup.clearCheck();
        rb1.setTextColor(dfRbcolor);
        rb2.setTextColor(dfRbcolor);
        rb3.setTextColor(dfRbcolor);
        rb4.setTextColor(dfRbcolor);
        rb5.setTextColor(dfRbcolor);
        rb6.setTextColor(dfRbcolor);
        if(qcounter < totalQuestion) {
            currentQuestion = questionList.get(qcounter);
            tvQuestions.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            rb5.setText(currentQuestion.getOption5());
            rb6.setText(currentQuestion.getOption6());
            qcounter++;
            btnNext.setText("Câu tiếp theo");
            tvQuestionNo.setText("Question : " + qcounter + "/" + totalQuestion);
            answered = false;
        }
        else {
            finish();
        }
    }

    private void addQuestions() {
        questionList.add(new QuizModel("Híu có gay không ? ", "Có", "Yes" , "Tất cả câu trên", "có", "có", "có", 3));

    }
}