package com.unilabs.gestionhospital.Clases; // Adjust package as needed

public class Hospital {
    // Private static instance variable
    private static Hospital instance;

    // Hospital attributes
    private String nombre;
    private String direccion;
    // Add other attributes as needed

    // Private constructor
    private Hospital() {
        // Initialize with default values or leave empty
    }

    // Public static method to get the instance
    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }
        return instance;
    }

    // Getters and setters for Hospital properties
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

    // Other Hospital methods
}