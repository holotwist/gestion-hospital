package com.unilabs.gestionhospital.repository;

import com.unilabs.gestionhospital.model.Enfermedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnfermedadRepository extends JpaRepository<Enfermedad, Long> {
}