package com.example.depresol;

public class Find {
    private final int id;
    private final String name;
    private final String numberOfCourses;
    private final int imageResource;

    public Find(int id, String name, String numberOfCourses, int imageResource) {
        this.id = id;
        this.name = name;
        this.numberOfCourses = numberOfCourses;
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Find{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfCourses='" + numberOfCourses + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfCourses() {
        return numberOfCourses;
    }

    public int getImageResource() {
        return imageResource;
    }
}
