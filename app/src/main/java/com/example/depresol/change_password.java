package com.example.depresol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class change_password extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        auth = FirebaseAuth.getInstance();
        txt = findViewById(R.id.thaydoi_password_Profile);
        btn = findViewById(R.id.editPassword2_profile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt.getText().toString()!=""){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }
}