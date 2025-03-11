package com.unilabs.gestionhospital.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass // Indica que esta clase es una superclase mapeada, no una entidad en sí misma.
                  // Nos evita problemas con JPA
@Data
@NoArgsConstructor
public class Persona {
    private String nombre;
    private byte edad;
    private String identificacion;
    private String telefono;

    public Persona(String nombre, byte edad, String identificacion, String telefono){
        this.nombre = nombre;
        this.edad = edad;
        this.identificacion = identificacion;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", identificacion='" + identificacion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}