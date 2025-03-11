package com.unilabs.gestionhospital.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Enfermedad implements Cloneable {
    private String nombre;
    private String descripcion;
    private Medicamento medicamento;

    public Enfermedad(String nombre, String descripcion, Medicamento medicamento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.medicamento = medicamento;
    }

    @Override
    public Enfermedad clone() throws CloneNotSupportedException {
        try {
            return (Enfermedad) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
