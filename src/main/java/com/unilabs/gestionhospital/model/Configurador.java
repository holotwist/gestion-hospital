package com.unilabs.gestionhospital.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Configurador {

    private static Configurador instance;
    private String horarioAtencion;
    private int maxPacientesPorMedico;
    private String reglasFacturacion;

    private Configurador() {
        // Valores por defecto del configurador
        this.horarioAtencion = "9:00 - 18:00";
        this.maxPacientesPorMedico = 20;
        this.reglasFacturacion = "Pago estándar por consulta.";
    }

    public static synchronized Configurador getInstance() {
        if (instance == null) {
            instance = new Configurador();
        }
        return instance;
    }

    // Generalmente, la configuración se actualiza, pero el objeto persiste.
    public static void reset() {
        instance = null; // Permite que se cree una nueva instancia la próxima vez.
    }
}