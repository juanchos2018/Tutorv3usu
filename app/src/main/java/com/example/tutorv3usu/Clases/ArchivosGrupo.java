package com.example.tutorv3usu.Clases;

public class ArchivosGrupo {

    String nombre;
    String ruta;
    String fecha;
    String id;

    public ArchivosGrupo(String id,String nombre, String ruta, String fecha) {
        this.id=id;


        this.nombre = nombre;
        this.ruta = ruta;
        this.fecha = fecha;
    }
    public ArchivosGrupo() {

    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
