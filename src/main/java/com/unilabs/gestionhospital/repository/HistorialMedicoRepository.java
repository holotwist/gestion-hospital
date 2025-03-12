package com.unilabs.gestionhospital.repository;

import com.unilabs.gestionhospital.model.HistorialMedico;
import com.unilabs.gestionhospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    Optional<HistorialMedico> findByPaciente(Paciente paciente);
}