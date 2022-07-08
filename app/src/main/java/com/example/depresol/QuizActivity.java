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
                ans[cnt] = currentQuestion.getOption0();
            }
            if(rb2.isChecked()) {
                ans[cnt] = currentQuestion.getOption1();
            }
            if(rb3.isChecked()) {
                ans[cnt] = currentQuestion.getOption2();
            }
            if(rb4.isChecked()) {
                ans[cnt] = currentQuestion.getOption3();
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
        if(qcounter < totalQuestion) {
            currentQuestion = questionList.get(qcounter);
            tvQuestions.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption0());
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
        //questionList.add(new QuizModel("Híu có gay không ? ", "Có", "Yes" , "Tất cả câu trên", "có", "có", "có", 3));
        questionList.add(new QuizModel("Bạn có thường xuyên có cảm giác buồn bã không?" , "Mình không cảm thấy buồn bã" ,"Nhiều khi mình có cảm giác buồn bã và chán nản" , "Mình thường có cảm giác buồn bã, chán nản thường trực và không thể dứt ra được" , "Mình cảm giác buồn bã sâu sắc, đau khổ, cảm thấy bản thân bất hạnh"  , 1));
        questionList.add(new QuizModel("Bạn có suy nghĩ thế nào về tương lai?" , "Hoàn toàn không nản lòng, bi quan hay suy nghĩ tiêu cực về tương lai.", "Suy nghĩ nhiều về tương lai với cảm giác chán nản và bi quan" , "Không cảm thấy mong đợi bất cứ điều gì trong tương lai" , "Cảm thấy cuộc sống hiện tại đang rất tệ và sẽ tệ hơn trong tương lai" , 1));
        questionList.add(new QuizModel("Bạn có thấy mình là một người thất bại không?" , "Không có cảm giác thất bại", "Có cảm giác bản thân thất bại, yếu kém hơn những người xung quanh" , "Cảm giác bản thân đã có quá nhiều thất bại trong cuộc sống/ Hoặc có cảm giác bản thân rất ít khi làm được điều gì đáng giá và có ý nghĩa" , "Cảm thấy bản thân chính xác là một kẻ thất bại" , 1));
        questionList.add(new QuizModel("Bạn có bất mãn với cuộc sống hiện tại của chính mình không?" , "Không bất mãn với cuộc sống,vẫn có hứng thú với những sở thích trước đây", "Luôn cảm thấy buồn và giảm hứng thú với những sở thích trước đây" , "Còn rất ít sự thích thú cho những điều mà bản thân yêu thích trước đây và cảm thấy không thỏa mãn về bất cứ điều gì trong cuộc sống" , "Mất hứng thú hoàn toàn với tất cả các sở thích" , 1));
        questionList.add(new QuizModel("Bạn có tự dằn vặt mình với nhũng lỗi lầm nào mình gây ra không?" , "Biết được bản thân không gây ra bất cứ tội lỗi nào nghiêm trọng cả", "Dành nhiều thời gian suy nghĩ, dằn vặt vì nghĩ bản thân là kẻ vô dụng, tội lỗi và không xứng đáng" , "Luôn cho rằng bản thân tồi tệ và không xứng đáng với những điều tốt đẹp" , "Luôn luôn cho rằng bản thân đã phạm phải tội lỗi nghiêm trọng" , 1));
        questionList.add(new QuizModel("Bạn có bao giờ nghĩ mình đang bị trừng phạt chưa?" , "Không có cảm giác đang bị trừng phạt", "ó cảm giác những điều xui rủi, tệ hại sẽ đến với bản thân" , "Cảm thấy bản thân chắc chắn sẽ bị trừng phạt" , "Muốn bị trừng phạt để thoát khỏi cảm giác tội lỗi, hối hận" , 1));
        questionList.add(new QuizModel("Bạn có bao giờ thấy đánh mình không còn niềm tin vào chính mình chưa?" , "Bản thân không có gì thay đổi và không cảm thấy thất vọng về bản thân", "Cảm thấy thất vọng và mất lòng tin về bản thân" , "Cảm thấy chán ghét bản thân, thậm chí ghê tởm chính mình" , "Căm thù bản thân và ghét bản thân sâu sắc" , 1));
        questionList.add(new QuizModel("Bạn sẽ đối mặt thế nào với những lồi lầm hay với chỉnh bản thân bạn?" , "Không phê phán hay đổ lỗi cho bản thân", "Phê phán chính mình nhiều hơn trước kia" , "Khiển trách chính mình vì những lỗi lầm đã gây ra" , "Xu hướng đổ lỗi cho bản thân trước những điều không may xảy ra" , 1));
        questionList.add(new QuizModel("Bạn có đã/đang có ý định nghĩ đến việc kết thúc cuộc sống này chưa?" , "Không có ý nghĩ tự sát và không có bất cứ ý nghĩ nào về việc tự làm hại bản thân", "Có ý nghĩ tự sát nhưng không thực hiện" , "Có cảm giác gia đình sẽ tốt hơn nếu bản thân chết đi" , "Có suy nghĩ tự tử nếu có cơ hội" , 1));
        questionList.add(new QuizModel("Bạn có khóc nhiều không?" , "Không khóc lóc nhiều hơn trước kia", "Khóc nhiều hơn trước và trở nên nhạy cảm hơn" , "Khóc thường xuyên và không kiểm soát được hành vi của chính mình" , "Muốn khóc nhưng không thể khóc được" , 1));
        questionList.add(new QuizModel("Bạn có căng thẳng nhiều không?" , "Không căng thẳng, hay bồn chồn nhiều hơn trước kia", "Dễ cáu kỉnh, bực tức hơn trước kia" , "Luôn luôn cáu kỉnh và khó kiểm soát được sự tức giận" , "Kích động, bồn chồn không thể kiểm soát và thường phải đi lại hoặc làm việc gì đó để giải tỏa" , 1));
        questionList.add(new QuizModel("Mọi người xung quanh bạn có hay quan tâm đến bạn không?" , "Vẫn giữ được sự quan tâm đến mọi người và các hoạt động yêu thích như trước đây", "Ít quan tâm đến mọi người và mọi thứ xung quanh" , "Mất hầu hết sự quan tâm đến mọi thứ và những người xung quanh, kể cả người thân và bạn bè thân thiết" , "Không quan tâm và cũng không cần đến bất cứ ai" , 1));
        questionList.add(new QuizModel("Việc quyết định vấn đề có gây ra khó khăn gì cho bạn không?" , "Vẫn có thể quyết định mọi thứ một cách dễ dàng và chính xác như trước đây", "Khó khăn đưa ra quyết định hơn so với trước đây" , "Thường chỉ đưa ra quyết định khi có sự hỗ trợ và giúp đỡ từ những người xung quanh" , " Không thể quyết định bất cứ việc gì – kể cả khi có sự hỗ trợ từ người thân và bạn bè" , 1));
        questionList.add(new QuizModel("Ngoại hình có phải là vấn đề đối với bạn?" , "Không cảm thấy bản thân xấu xí", "Cảm thấy buồn bã vì bản thân già nua, thiếu hấp dẫn" , "Cảm thấy bản thân vô dụng, xấu xí hơn những người xung quanh" , "Cho rằng bản thân xấu xí, ghê tởm và hoàn toàn vô dụng" , 1));
        questionList.add(new QuizModel("Sức khỏe bạn vẫn ổn chứ?" , "Vẫn giữ được sức khỏe và sự năng động như trước đây", "Cảm thấy sức khỏe kém hơn, thường xuyên mệt mỏi và uể oải" , "Không đủ sức lực để thực hiện nhiều việc như trước đây" , "Không thể hoàn thành bất cứ việc gì" , 1));
        questionList.add(new QuizModel("Bạn có ngủ đủ giấc không?" , "Giấc ngủ không thay đổi so với trước kia", "Ngủ hơi ít hoặc hơi nhiều so với trước kia" , "Ngủ nhiều hoặc ngủ ít hơn đáng kể so với trước kia" , "Ngủ liên tục nhiều giờ trong ngày hoặc ngủ rất ít, giấc ngủ chỉ kéo dài 2 – 3 giờ, ngủ chập chờn và không sâu giấc" , 1));
        questionList.add(new QuizModel("Bạn có thường hay nổi nóng với những người xung quanh bạn không?" , "Không dễ cáu kỉnh, nổi nóng hơn trước", " Dễ nổi nóng, bực bội, cáu kỉnh hơn trước kia" , "Cáu kỉnh, bực bội và tức giận nhiều hơn so với thời gian trước đây" , "Luôn cảm thấy bực bội và cáu kỉnh" , 1));
        questionList.add(new QuizModel("Cân nặng hiện tại của bạn so với trước như thế nào" , "Không sụt cân và vẫn giữ được khả năng chú ý như trước đây", "Sụt cân trên 2 kg/ Không giữ được sự chú ý tốt như trước đây" , "Sụt cân trên 4kg/ Không tập trung trong quá trình học tập và làm việc" , "Sụt cân trên 6 kg" , 1));
        questionList.add(new QuizModel("Bạn có quan tâm tới sức khỏe bản thân mình không?" , "Không lo lắng về sức khỏe nhiều hơn trước", "Dễ mệt mỏi" , "Luôn cảm thấy mệt mỏi khi làm tất cả mọi thứ" , "Quá mệt mỏi khi làm bất kỳ việc gì" , 1));
        questionList.add(new QuizModel("Bạn có tần suất ham muốn tình dục như thế nào?" , "Không có thay đổi về ham muốn, hứng thú tình dục so với trước đây", "Ít, giảm hứng thú tình dục so với trước đây" , "Rất ít khi có ham muốn tình dục" , "Mất hoàn toàn ham muốn tình dục" , 1));
    }
}