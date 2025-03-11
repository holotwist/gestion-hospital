package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.Paciente;
import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("pacientes-especiales") // ruta de esta vista
@PageTitle("Pacientes Especiales")
public class PacientesEspecialesView extends VerticalLayout {

    private final HospitalService hospitalService;
    private final Grid<Paciente> gridPacientes = new Grid<>(Paciente.class);

    public PacientesEspecialesView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        add(new H2("Pacientes Especiales"));

        gridPacientes.setColumns("nombre", "edad", "id", "telefono");

        Button palindromosButton = new Button("Pacientes Palíndromos", e -> mostrarPacientesPalindromos());
        Button vocalesIgualesButton = new Button("Pacientes con 2 Vocales Iguales", e -> mostrarPacientesVocalesIguales());

        add(gridPacientes, palindromosButton, vocalesIgualesButton);
    }
    private void mostrarPacientesPalindromos() {
        gridPacientes.setItems(hospitalService.obtenerPacientesPalindromos());
    }

    private void mostrarPacientesVocalesIguales() {
        gridPacientes.setItems(hospitalService.obtenerPacientesConDosVocalesIguales());
    }

}