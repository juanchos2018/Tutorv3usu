package com.example.tutorv3usu.ClasesTutor;

public class cursotutor {
    private  String id;
    private String codigotutor;
    private String curso;

    public cursotutor(String id, String curso) {
        this.id=id;
        this.curso = curso;
    }

    public cursotutor() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigotutor() {
        return codigotutor;
    }

    public void setCodigotutor(String codigotutor) {
        this.codigotutor = codigotutor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
