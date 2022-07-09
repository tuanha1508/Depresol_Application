package com.example.depresol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName,edtMail,edtPass;
    Button btnLogin;
    RadioButton radio_chose_gv , radio_chose_hs;
    FirebaseAuth auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    int role = 1;
    private String filename = "Storage.txt";
    private String filepath = "Super_mystery_folder";
    File myInternalFile;
    String uid , uid_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        setContentView(R.layout.activity_register);
        btnLogin = findViewById(R.id.cirRegisterButton);
        edtName = findViewById(R.id.editTextName);
        edtMail = findViewById(R.id.editTextMail);
        edtPass = findViewById(R.id.editTextPassword);
        radio_chose_gv = findViewById(R.id.checked_gv);
        radio_chose_hs = findViewById(R.id.checked_hs);
        auth = FirebaseAuth.getInstance();

        this.radio_chose_gv.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) role = 1; else role = 0;
                String hi = "GV";
                if(role == 1) hi = "1"; else hi = "0";
                Log.d("dang chon ", hi);
            }
        });

//        this.radio_chose_hs.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) role = 0; else role = 1;
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtMail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Hãy nhập địa chỉ email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Hãy nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn!. Tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("t",task.getException().toString());
                                } else {

                                    try {
                                        String data = "1";
                                        FileOutputStream fos = new FileOutputStream(myInternalFile);
                                        fos.write(data.getBytes());
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    uid = auth.getUid();
                                    rootNode = FirebaseDatabase.getInstance();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    //FirebaseAuth user = FirebaseAuth.getInstance();
                                    //uid = user.getUid();
                                    //reference = rootNode.getReference("users/"+"tmp");
                                    uid_new = uid;
                                    reference = rootNode.getReference("users/"+uid_new);
                                    String tt = "GV";
                                    if(role == 1) tt = "GV"; else tt = "HS";
                                    Users login_users = new Users(edtMail.getText().toString(),edtPass.getText().toString(),tt,edtName.getText().toString(),"");
                                    login_users.role = tt;
                                    reference.setValue(login_users);
                                    Log.d("Done", "Dang ky thanh cong");

                                    Intent intent = new Intent(RegisterActivity.this , MainActivity_Menu.class);
                                    intent.putExtra("name_user", edtName.getText().toString());
                                    intent.putExtra("url_avatar", "");
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    public void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}