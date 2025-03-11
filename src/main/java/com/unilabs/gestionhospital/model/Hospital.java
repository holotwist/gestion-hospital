package com.unilabs.gestionhospital.model;

public class Hospital {

    private static Hospital instance;


    private String nombre;
    private String direccion;

    private Hospital() {

    }


    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }
        return instance;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}