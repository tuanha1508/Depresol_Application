package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity_settings extends AppCompatActivity {
    Switch darkModeSwitch;
    FirebaseAuth mFirebaseAuth;
    TextView log_out;
    File myInternalFile;
    private String filename = "Storage.txt";
    private String filepath = "Super_mystery_folder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);


        mFirebaseAuth = FirebaseAuth.getInstance();

        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        setContentView(R.layout.activity_settings);
        log_out = findViewById(R.id.log_out);
        setDarkModeSwitch();

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                try {
                    String data = "0";
                    FileOutputStream fos = new FileOutputStream(myInternalFile);
                    fos.write(data.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Activity_settings.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDarkModeSwitch(){
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(new DarkModePrefManager(this).isNightMode());
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(Activity_settings.this);
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        });
    }
}