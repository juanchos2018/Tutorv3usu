package com.example.tutorv3usu.Clases;

public class Usuaros {

    private String nombre, verified;
    private String user_image;
    private String apellido;
    private String user_thumb_image;
    private String telefono;

    public Usuaros() {
    }

    public Usuaros(String nombre,String verified, String apellido, String user_image, String telfono) {
        this.nombre = nombre;
        this.verified = verified;
        this.apellido=apellido;
        this.user_image = user_image;
        this.telefono = telfono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setUser_thumb_image(String user_thumb_image) {
        this.user_thumb_image = user_thumb_image;
    }


}
