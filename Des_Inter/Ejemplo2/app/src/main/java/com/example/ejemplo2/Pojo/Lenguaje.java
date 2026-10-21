package com.example.ejemplo2.Pojo;

public class Lenguaje {
    private String nombre;
    private String descripcion;

    public Lenguaje(String nombre, String descripcion){
        this.descripcion=descripcion;
        this.nombre=nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Lenguaje{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
