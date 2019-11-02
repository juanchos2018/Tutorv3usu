package com.example.tutorv3usu.Clases;

public class msn {
    String  nombre;
    String mensaje;

    public  msn(){

    }
    public msn(String nombre, String mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
