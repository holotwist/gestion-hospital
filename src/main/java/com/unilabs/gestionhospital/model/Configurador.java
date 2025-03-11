package com.unilabs.gestionhospital.model;

public class Configurador {
    public void configurarHospital(Hospital hospital, String nombre, String direccion) {
        hospital.setNombre(nombre);
        hospital.setDireccion(direccion);

    }
}