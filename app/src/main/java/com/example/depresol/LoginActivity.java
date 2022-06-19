package com.example.depresol;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText edtMail , edtPass;
    String emailPattern = "[a-zA-Z0-9._+]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private String filename = "Storage.txt";
    private String filepath = "Super_mystery_folder";
    File myInternalFile;
    boolean is_login = false;
    String name_user = null , url_avatar = null;
    TextView txt_forget_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        check_is_login();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        if(is_login == true){
            //startActivity(new Intent(this,MainActivity_Menu.class));
            Intent intent = new Intent(this, MainActivity_Menu.class);
            intent.putExtra("name_user", name_user);
            intent.putExtra("url_avatar", url_avatar);
            startActivity(intent);
        }
        login =  findViewById(R.id.cirLoginButton);
        edtMail =  findViewById(R.id.editEditTextMail);
        edtPass =  findViewById(R.id.editTextPassword);
        progressDialog  = new ProgressDialog(this);
        txt_forget_pass = findViewById(R.id.forget_pass);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtMail.getText().toString();
                String password = edtPass.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        edtPass.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    try {
                                        String data = "1";
                                        FileOutputStream fos = new FileOutputStream(myInternalFile);
                                        fos.write(data.getBytes());
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Intent intent = new Intent(LoginActivity.this, MainActivity_Menu.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forget_password_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void check_is_login(){
        String myData = "";
        //+ "\n" + "https://i.pinimg.com/736x/18/ab/30/18ab3055867d8215889ff0bb53781ecc.jpg"
//        try {
//            String data = "1";
//            FileOutputStream fos = new FileOutputStream(myInternalFile);
//            fos.write(data.getBytes());
//            data  = "\nHiếu";
//            fos.write(data.getBytes(StandardCharsets.UTF_8));
//            data  = "\nhttps://i.pinimg.com/736x/18/ab/30/18ab3055867d8215889ff0bb53781ecc.jpg";
//            fos.write(data.getBytes(StandardCharsets.UTF_8));
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            FileInputStream fis = new FileInputStream(myInternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            String strLine ;
            int i = 1;
            while ((strLine = br.readLine()) != null) {
                if(i==1) myData = myData + strLine;
                if(i==2) name_user = strLine;
                if(i==3) url_avatar = strLine;
                i++;
            }
            int kl = Integer.parseInt(myData);
            if(kl == 0) is_login = false; else is_login = true;
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}