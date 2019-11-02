package com.example.tutorv3usu.ClasesAlumno;

public class Tutores {
    String idtutor;

    String nombre;
    String telefono;
    String correo;

    public Tutores(String idtutor, String nombre, String telefono, String correo) {
        this.idtutor = idtutor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Tutores(){

    }
    public String getIdtutor() {
        return idtutor;
    }

    public void setIdtutor(String idtutor) {
        this.idtutor = idtutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
