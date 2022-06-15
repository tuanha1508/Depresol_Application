package com.example.depresol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Forget_password_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user ;
    Button send_mail;
    EditText edt;
    TextView have_acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        send_mail = findViewById(R.id.btn_send_mail);
        edt = findViewById(R.id.editSendTextMail);
        have_acc = findViewById(R.id.have_acc);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Bạn nhập email để bọn mình gửi nha ~.~", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Forget_password_Activity.this, "Bạn kiểm tra email nhé", Toast.LENGTH_SHORT).show();
                            Log.d("Done: " , "Send email succesfully");
                        } else {
                            Toast.makeText(Forget_password_Activity.this, "Lỗi khi gửi email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forget_password_Activity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }
}