package com.example.depresol;

import android.widget.ImageView;

public class Information {
    String   url , title , description ;
    int img , avatar;

    public Information( String url, String title,  String description ,  int img , int avatar) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.img = img;
        this.avatar = avatar;
    }
}
