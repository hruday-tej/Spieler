package com.example.spieler.Models;

public class User {
    private String Image;
    private String Name;
    private String Status;


    public User(String image, String name, String status ) {

        Image = image;
        Name = name;
        Status = status;

    }
    public User(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
