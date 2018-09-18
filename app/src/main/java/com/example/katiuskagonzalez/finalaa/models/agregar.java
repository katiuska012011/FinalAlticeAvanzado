package com.example.katiuskagonzalez.finalaa.models;

public class agregar {

    public String Nombre;
    public String Descripcion;
    public String Ubicacion;
    public String Imagen;

    public agregar() {
    }

    public agregar(String nombre, String descripcion, String ubicacion, String imagen) {
        Nombre = nombre;
        Descripcion = descripcion;
        Ubicacion = ubicacion;
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}