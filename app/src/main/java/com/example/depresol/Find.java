package com.example.depresol;

public class Find {
    private final int id;
    private final String name;
    private final int imageResource;

    public Find(int id, String name, int imageResource) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Find{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
