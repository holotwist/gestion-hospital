package com.unilabs.gestionhospital.model.gestor;

import com.unilabs.gestionhospital.model.Cita;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorCita {
    private ArrayList<Cita> citas;

    public GestorCita() {
        this.citas = new ArrayList<>();
    }

    public void agregarCita(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("Cita cannot be null");
        }
        citas.add(cita);
    }

    public ArrayList<Cita> obtenerCitas() {
        return new ArrayList<>(citas);
    }

    public Cita buscarCitaPorFecha(LocalDate fecha) {
        for (Cita cita : citas) {
            if (cita.getFecha().equals(fecha)) {
                return cita;
            }
        }
        return null;
    }

    public boolean eliminarCita(Cita cita) {
        return citas.remove(cita);
    }

    public void actualizarCita(Cita citaExistente, Cita nuevaCita) {
        int index = citas.indexOf(citaExistente);
        if (index != -1) {
            citas.set(index, nuevaCita);
        } else {
            throw new IllegalArgumentException("Cita not found");
        }
    }
}