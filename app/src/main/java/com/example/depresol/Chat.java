package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.depresol.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backBtn = findViewById(R.id.back_Btn);
        final TextView nameTV = findViewById(R.id.name);
        final EditText messageEditText = findViewById(R.id.messageEditTxt);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final ImageView sendBtn = findViewById(R.id.send_Btn);

        // get data from messages adapter class
        final String getName = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nameTV.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}