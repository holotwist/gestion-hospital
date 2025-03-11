package com.unilabs.gestionhospital.model;

import lombok.Getter;

@Getter
public class Medicamento implements Cloneable {
    private String nombre;
    private String descripcion;
    private String tipo;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String fechaInicio;
    private String fechaFin;

    public Medicamento(String nombre, String descripcion, String tipo, String dosis, String frecuencia, String duracion, String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public Medicamento clone() {
        try {
            return (Medicamento) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
