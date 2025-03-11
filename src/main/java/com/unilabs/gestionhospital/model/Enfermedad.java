package com.unilabs.gestionhospital.model;

public class Enfermedad implements Cloneable {
    private String nombre;
    private String descripcion;
    private Medicamento medicamento;

    public Enfermedad(String nombre, String descripcion, Medicamento medicamento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.medicamento = medicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMedicamento(Medicamento medicamento) {
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
