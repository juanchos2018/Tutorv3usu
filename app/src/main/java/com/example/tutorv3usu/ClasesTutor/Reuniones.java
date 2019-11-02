package com.example.tutorv3usu.ClasesTutor;

public class Reuniones {

    String id;
    String titulo;
    String curso;
    String lugar;
    String fecha;
    String descripcion;



    public Reuniones(String id, String titulo,String curso,String lugar,String fehca,String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.curso=curso;
        this.lugar=lugar;
        this.fecha=fehca;
        this.descripcion=descripcion;

    }

    public Reuniones(){

    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
