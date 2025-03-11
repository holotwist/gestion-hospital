package com.unilabs.gestionhospital.model;

public class Paciente extends Persona implements Cloneable{
    public Paciente(String nombre, byte edad, String ID, String telefono) {
        super(nombre, edad, ID, telefono);
    }

    @Override
    public Paciente clone() {
        try {
            return (Paciente) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
