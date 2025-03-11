package com.unilabs.gestionhospital.model;

public class Persona {
    private String nombre;
    private byte edad;
    private String ID;
    private String telefono;

    public Persona(String nombre, byte edad, String ID, String telefono){
        this.nombre = nombre;
        this.edad = edad;
        this.ID = ID;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", ID='" + ID + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
