package com.unilabs.gestionhospital.model.gestor;

import com.unilabs.gestionhospital.model.Paciente;
import java.util.ArrayList;
import java.util.List;

public final class GestorPacientes {  // La clase es final, evita ser heredada

    private GestorPacientes() {
        // Constructor privado para evitar instanciación
        throw new AssertionError("GestorPacientes no debe ser instanciado.");
    }

    public static List<Paciente> PacientesPalindromos(List<Paciente> pacientes) {
        List<Paciente> pacientesPalindromos = new ArrayList<>();

        for (Paciente paciente : pacientes) {
            if (esPalindromo(paciente.getNombre())) {
                pacientesPalindromos.add(paciente);
            }
        }

        return pacientesPalindromos;

    }

    private static boolean esPalindromo(String texto) {
        String limpio = texto.replaceAll("\\s+", "").toLowerCase();
        String invertido = new StringBuilder(limpio).reverse().toString();
        return limpio.equals(invertido);
    }

    public static List<Paciente> PacientesVocalesIguales(List<Paciente> pacientes) {
        List<Paciente> pacientesVocales = new ArrayList<>();

        for (Paciente paciente :pacientes) {
            if (tieneDosVocalesIguales(paciente.getNombre())) {
                pacientesVocales.add(paciente);
            }
        }
        return pacientesVocales;
    }

    private static boolean tieneDosVocalesIguales(String nombre) {
        nombre = nombre.toLowerCase();
        String vocales = "aeiou";

        for (char vocal : vocales.toCharArray()) {
            int count = 0;
            for (char letra : nombre.toCharArray()) {
                if (letra == vocal) {
                    count++;
                }
                if (count >= 2) {
                    return true;
                }
            }
        }

        return false;
    }
}