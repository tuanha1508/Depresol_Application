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
                new Find(1, "Digital Marketing", "12 courses available", R.drawable.pic1),
                new Find(2, "Business", "50 courses available", R.drawable.pic2),
                new Find(3, "Development", "265 courses available", R.drawable.pic3),
                new Find(4, "Security", "18 courses available", R.drawable.pic4),
                new Find(5, "Ethical Hacking", "36 courses available", R.drawable.pic5),
                new Find(6, "Mobile", "145 courses available", R.drawable.pic6)

        );
    }

}
