package com.example.tutorv3usu.Clases;

public class Participantes {

    String codigoalumno;
    String nombrealumno;

    public Participantes(String codigoalumno, String nombrealumno) {
        this.codigoalumno = codigoalumno;
        this.nombrealumno = nombrealumno;
    }
    public Participantes() {

    }


    public String getCodigoalumno() {
        return codigoalumno;
    }

    public void setCodigoalumno(String codigoalumno) {
        this.codigoalumno = codigoalumno;
    }

    public String getNombrealumno() {
        return nombrealumno;
    }

    public void setNombrealumno(String nombrealumno) {
        this.nombrealumno = nombrealumno;
    }
}
