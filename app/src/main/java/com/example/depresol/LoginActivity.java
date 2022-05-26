package com.example.depresol;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    ImageView kl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);


    }
    public void onLoginClick(View view){
//        try{
//
//        } catch(Exception ex){
//            Log.d("Error " , ex.getMessage());
//        }
//
//        try{
//
//        } catch (Exception ex){
//            Log.e("Error ",ex.getMessage());
//        }
    }
}