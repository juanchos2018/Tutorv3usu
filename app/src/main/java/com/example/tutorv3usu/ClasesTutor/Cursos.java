package com.example.tutorv3usu.ClasesTutor;

public class Cursos {

    String id;
    String curso;
    public Cursos(){

    }
    public Cursos(String id, String curso) {
        this.id = id;
        this.curso = curso;
    }



    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

 /*   public String getNombre() {
        return curso;
    }

    public void setNombre(String nombre) {
        this.curso = nombre;
    }

  */
}
