package com.example.depresol;

import java.util.Arrays;
import java.util.List;

public class MyFind {

    private MyFind() {
    }

    public static MyFind get() {
        return new MyFind();
    }

    public List<Find> getData() {
        return Arrays.asList(
                new Find(1, "Violent", R.drawable.violence2),
                new Find(2, "Depress", R.drawable.depress),
                new Find(3, "Development" ,R.drawable.pic3),
                new Find(4, "Security", R.drawable.pic4),
                new Find(5, "Ethical Hacking", R.drawable.pic5),
                new Find(6, "Mobile", R.drawable.pic6)

        );
    }

}
