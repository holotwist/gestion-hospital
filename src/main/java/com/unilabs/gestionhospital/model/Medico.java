package com.unilabs.gestionhospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Medico extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especialidad;
    private String horario;

    public Medico(String nombre, byte edad, String identificacion, String telefono, String especialidad, String horario){
        super(nombre, edad, identificacion, telefono);
        this.especialidad = especialidad;
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "especialidad='" + especialidad + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}