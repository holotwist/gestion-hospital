package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.Cita;
import com.unilabs.gestionhospital.model.Medico;
import com.unilabs.gestionhospital.model.Paciente;
import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("citas") // ruta de esta vista
@PageTitle("Citas")
public class CitasView extends VerticalLayout {

    private final HospitalService hospitalService;
    private final Grid<Cita> gridCitas = new Grid<>(Cita.class);
    private final Binder<Cita> binderCita = new Binder<>(Cita.class);

    public CitasView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;

        add(new H2("Gestión de Citas"));
        setupCitas();
        refreshGrid();
    }

    private void setupCitas() {
        gridCitas.setColumns("fecha", "hora", "motivo");
        gridCitas.addColumn(cita -> cita.getPaciente().getNombre()).setHeader("Paciente");
        gridCitas.addColumn(cita -> cita.getMedico().getNombre()).setHeader("Médico");

        // Formulario para añadir citas
        DatePicker fechaCitaField = new DatePicker("Fecha");
        TimePicker horaCitaField = new TimePicker("Hora");
        TextField motivoCitaField = new TextField("Motivo");

        ComboBox<Paciente> pacienteComboBox = new ComboBox<>("Paciente");
        pacienteComboBox.setItems(hospitalService.obtenerTodosLosPacientes());
        pacienteComboBox.setItemLabelGenerator(Paciente::getNombre);

        ComboBox<Medico> medicoComboBox = new ComboBox<>("Médico");
        medicoComboBox.setItems(hospitalService.obtenerTodosLosMedicos());
        medicoComboBox.setItemLabelGenerator(Medico::getNombre);

        binderCita.forField(fechaCitaField).bind(Cita::getFecha, Cita::setFecha);
        binderCita.forField(horaCitaField).bind(Cita::getHora, Cita::setHora);
        binderCita.forField(motivoCitaField).bind(Cita::getMotivo, Cita::setMotivo);
        binderCita.forField(pacienteComboBox).bind(Cita::getPaciente, Cita::setPaciente);
        binderCita.forField(medicoComboBox).bind(Cita::getMedico, Cita::setMedico);

        // Botones para Citas
        Button addCitaButton = new Button("Añadir Cita", e -> {
            Cita cita = new Cita();
            if(binderCita.writeBeanIfValid(cita)){
                hospitalService.guardarCita(cita);
                refreshGrid();
                binderCita.readBean(new Cita());
            }
        });

        Button updateCitaButton = new Button("Actualizar Cita", e ->{
            Cita selectedCita = gridCitas.asSingleSelect().getValue();
            if(selectedCita != null && binderCita.writeBeanIfValid(selectedCita)){
                hospitalService.guardarCita(selectedCita);
                refreshGrid();
            }
        });

        Button deleteCitaButton = new Button("Eliminar", e ->{
            Cita selectedCita = gridCitas.asSingleSelect().getValue();
            if(selectedCita!= null){
                hospitalService.eliminarCita(selectedCita.getId());
                refreshGrid();
            }
        });

        //Buscador por fecha
        DatePicker fechaBusqueda = new DatePicker("Buscar por Fecha");
        Button buscarCitasButton = new Button("Buscar", e -> {
            List<Cita> citasEnFecha = hospitalService.obtenerCitasPorFecha(fechaBusqueda.getValue());
            gridCitas.setItems(citasEnFecha);
        });
        Button mostrarTodasCitasButton = new Button("Mostrar todas", e -> refreshGrid());


        gridCitas.asSingleSelect().addValueChangeListener(event -> {
            binderCita.setBean(event.getValue());  //Carga los datos en los campos.
        });

        add(gridCitas, new HorizontalLayout(fechaCitaField, horaCitaField, motivoCitaField, pacienteComboBox, medicoComboBox),
                new HorizontalLayout(addCitaButton,updateCitaButton, deleteCitaButton), new HorizontalLayout(fechaBusqueda, buscarCitasButton, mostrarTodasCitasButton));
    }
    private void refreshGrid() {
        gridCitas.setItems(hospitalService.obtenerTodasLasCitas());
    }
}