package com.example.spieler;

public class User {
    public static String Name;
    public static String Status;
    public static String Image;

    public User(){

    }

    public User(String name, String status, String image) {
        Name = name;
        Status = status;
        Image = image;
    }

    public static String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public static String getStatus() {
        return Status;
    }

    public void  setStatus(String status) {
        Status = status;
    }

    public static String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
