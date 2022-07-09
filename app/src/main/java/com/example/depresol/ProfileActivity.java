package com.example.depresol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ProfileActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    TextView name , birth , sdt , email;
    Button birhtday , btn;
    DatePickerDialog dt;
    FirebaseAuth auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String uid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        birhtday = findViewById(R.id.btn_birthday);
        dateView = findViewById(R.id.Txtbirthdate);
        btn = findViewById(R.id.thaydoiProfile);
        name = findViewById(R.id.edtName_profile);
        birth = findViewById(R.id.Txtbirthdate);
        sdt = findViewById(R.id.edtDT_profile);
        email = findViewById(R.id.edtMail_profile);
        auth = FirebaseAuth.getInstance();


        birhtday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chose_day();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = auth.getUid();
                if (name.getText().toString() != "") {
                    reference.child(uid).child("name").setValue(name.getText().toString());
                }
                if (birth.getText().toString() != "") {
                    reference.child(uid).child("birthday").setValue(birth.getText().toString());
                }
                if (sdt.getText().toString() != "") {
                    reference.child(uid).child("sdt").setValue(sdt.getText().toString());
                }
                if(email.getText().toString()!=""){
                    reference.child(uid).child("mail").setValue(email.getText().toString());
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String hi = email.getText().toString();
                    user.updateEmail(hi).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
                Intent intent = new Intent(ProfileActivity.this , Activity_settings.class);
                startActivity(intent);
            }
        });
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
    private void chose_day(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog( this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateView.setText(simpleDateFormat.format(calendar.getTime()));
            }
        } , year,month,day);
        datePickerDialog.show();
    }
}