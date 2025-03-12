package com.unilabs.gestionhospital.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String tipo;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "historial_medico_id")
    private HistorialMedico historialMedico;


    public Medicamento(String nombre, String descripcion, String tipo, String dosis, String frecuencia, String duracion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    //Constructor que incluye historialMedico, para asegurar bidireccionalidad (resolución para JPA)
    public Medicamento(String nombre, String descripcion, String tipo, String dosis, String frecuencia, String duracion, LocalDate fechaInicio, LocalDate fechaFin, HistorialMedico historialMedico) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.historialMedico = historialMedico;
    }
}