package com.example.tutorv3usu.Clases;

public class CursosAlumno {

    String id;
    String curso;
    String nombre;

    public CursosAlumno(String id, String curso, String nombre) {
        this.id = id;
        this.curso = curso;
        this.nombre = nombre;
    }

    public CursosAlumno(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
