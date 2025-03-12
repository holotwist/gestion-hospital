package com.unilabs.gestionhospital.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> enfermedades;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "historialMedico")
    private List<Medicamento> medicamentos;


    public HistorialMedico() {}

    public HistorialMedico(Paciente paciente, List<String> enfermedades, List<Medicamento> medicamentos) {
        this.paciente = paciente;
        this.enfermedades = enfermedades;
        this.medicamentos = medicamentos;
    }
}