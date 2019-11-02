package com.example.tutorv3usu.Modelo;

public class Amigos {

    String user_name;
    String user_thumb_image;
    String date;

    public Amigos() {
    }

    public Amigos(String user_name, String user_thumb_image, String date) {
        this.user_name = user_name;
        this.user_thumb_image = user_thumb_image;
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_thumb_image() {
        return user_thumb_image;
    }

    public void setUser_thumb_image(String user_thumb_image) {
        this.user_thumb_image = user_thumb_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
