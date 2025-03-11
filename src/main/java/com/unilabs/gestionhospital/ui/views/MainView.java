package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.Cita;
import com.unilabs.gestionhospital.model.Medico;
import com.unilabs.gestionhospital.model.Paciente;
import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final HospitalService hospitalService;

    private final Grid<Paciente> gridPacientes = new Grid<>(Paciente.class);
    private final Grid<Medico> gridMedicos = new Grid<>(Medico.class);
    private final Grid<Cita> gridCitas = new Grid<>(Cita.class);

    private final Binder<Paciente> binderPaciente = new Binder<>(Paciente.class);
    private final Binder<Medico> binderMedico = new Binder<>(Medico.class);
    private final Binder<Cita> binderCita = new Binder<>(Cita.class);


    public MainView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;

        add(new H2("Gestión del Hospital"));

        // Configuración del Hospital
        add(new H2("Configuración del Hospital"));
        Button showConfigButton = new Button("Mostrar Configuración", e -> mostrarConfiguracion());
        add(showConfigButton);


        // Pacientes
        setupPacientes();

        // Médicos
        setupMedicos();

        // Citas
        setupCitas();

        //Pacientes especiales
        add(new H2("Pacientes Especiales"));
        Button palindromosButton = new Button("Pacientes Palíndromos", e -> mostrarPacientesPalindromos());
        Button vocalesIgualesButton = new Button("Pacientes con 2 Vocales Iguales", e -> mostrarPacientesVocalesIguales());
        add(palindromosButton, vocalesIgualesButton);

        refreshGrids();
    }

    private void mostrarConfiguracion(){
        com.unilabs.gestionhospital.model.Configurador configurador = com.unilabs.gestionhospital.model.Configurador.getInstance();
        String configText = "Horario de Atención: " + configurador.getHorarioAtencion() +
                "\nMáximo Pacientes por Médico: " + configurador.getMaxPacientesPorMedico() +
                "\nReglas de Facturación: " + configurador.getReglasFacturacion();

        Notification.show(configText, 5000, Notification.Position.MIDDLE);
    }


    private void setupPacientes() {
        add(new H2("Pacientes"));

        gridPacientes.setColumns("nombre", "edad", "id", "telefono");
        gridPacientes.addComponentColumn(this::createCloneButton).setHeader("Clonar");

        TextField nombrePacienteField = new TextField("Nombre");
        TextField edadPacienteField = new TextField("Edad");
        TextField idPacienteField = new TextField("ID");
        TextField telefonoPacienteField = new TextField("Teléfono");


        binderPaciente.forField(nombrePacienteField).bind(Paciente::getNombre, Paciente::setNombre);
        binderPaciente.forField(edadPacienteField).bind(p -> String.valueOf(p.getEdad()), (p, s) -> p.setEdad(parseByte(s, (byte)0)));
        binderPaciente.forField(idPacienteField).bind(Paciente::getIdentificacion, Paciente::setIdentificacion);
        binderPaciente.forField(telefonoPacienteField).bind(Paciente::getTelefono, Paciente::setTelefono);

        Button addPacienteButton = new Button("Añadir Paciente", e -> {
            Paciente paciente = new Paciente();
            if (binderPaciente.writeBeanIfValid(paciente)) {
                hospitalService.guardarPaciente(paciente);
                refreshGrids();
                binderPaciente.readBean(new Paciente()); // Limpiar el form
            }
        });

        Button updatePacienteButton = new Button("Actualizar", e -> {
            Paciente selectedPaciente = gridPacientes.asSingleSelect().getValue();
            if (selectedPaciente != null && binderPaciente.writeBeanIfValid(selectedPaciente)) {
                hospitalService.guardarPaciente(selectedPaciente);
                refreshGrids();
            }
        });

        Button deletePacienteButton = new Button("Eliminar", e -> {
            Paciente selectedPaciente = gridPacientes.asSingleSelect().getValue();
            if (selectedPaciente != null) {
                hospitalService.eliminarPaciente(selectedPaciente.getId());
                refreshGrids();
            }
        });


        gridPacientes.asSingleSelect().addValueChangeListener(event -> {
            binderPaciente.setBean(event.getValue());  //Carga los datos en los campos
        });

        add(gridPacientes, new HorizontalLayout(nombrePacienteField, edadPacienteField, idPacienteField, telefonoPacienteField),
                new HorizontalLayout(addPacienteButton, updatePacienteButton, deletePacienteButton));
    }
    private Button createCloneButton(Paciente paciente) {
        return new Button("Clonar", e -> {
            try {
                Paciente clonedPaciente = hospitalService.clonarPaciente(paciente.getId());
                hospitalService.guardarPaciente(clonedPaciente);
                refreshGrids();
                Notification.show("Paciente clonado con éxito.", 3000, Notification.Position.BOTTOM_CENTER);
            } catch (CloneNotSupportedException ex) {
                Notification.show("Error al clonar paciente: " + ex.getMessage(), 5000, Notification.Position.BOTTOM_CENTER);
            }
        });
    }


    private void setupMedicos() {
        add(new H2("Médicos"));

        gridMedicos.setColumns("nombre", "edad", "id", "telefono", "especialidad", "horario");

        TextField nombreMedicoField = new TextField("Nombre");
        TextField edadMedicoField = new TextField("Edad");
        TextField idMedicoField = new TextField("ID");
        TextField telefonoMedicoField = new TextField("Teléfono");
        TextField especialidadMedicoField = new TextField("Especialidad");
        TextField horarioMedicoField = new TextField("Horario");

        binderMedico.forField(nombreMedicoField).bind(Medico::getNombre, Medico::setNombre);
        binderMedico.forField(edadMedicoField).bind(m -> String.valueOf(m.getEdad()), (m, s) -> m.setEdad(parseByte(s, (byte)0)));
        binderMedico.forField(idMedicoField).bind(Medico::getIdentificacion, Medico::setIdentificacion);
        binderMedico.forField(telefonoMedicoField).bind(Medico::getTelefono, Medico::setTelefono);
        binderMedico.forField(especialidadMedicoField).bind(Medico::getEspecialidad, Medico::setEspecialidad);
        binderMedico.forField(horarioMedicoField).bind(Medico::getHorario, Medico::setHorario);



        Button addMedicoButton = new Button("Añadir Médico", e -> {
            Medico medico = new Medico();
            if (binderMedico.writeBeanIfValid(medico)) {
                hospitalService.guardarMedico(medico);
                refreshGrids();
                binderMedico.readBean(new Medico()); //Limpiar form
            }
        });

        Button updateMedicoButton = new Button("Actualizar", e -> {
            Medico selectedMedico = gridMedicos.asSingleSelect().getValue();
            if(selectedMedico != null && binderMedico.writeBeanIfValid(selectedMedico)){
                hospitalService.guardarMedico(selectedMedico);
                refreshGrids();
            }
        });

        Button deleteMedicoButton = new Button("Eliminar", e ->{
            Medico selectedMedico = gridMedicos.asSingleSelect().getValue();
            if (selectedMedico != null){
                hospitalService.eliminarMedico(selectedMedico.getId());
                refreshGrids();
            }
        });

        gridMedicos.asSingleSelect().addValueChangeListener(event ->{
            binderMedico.setBean(event.getValue());
        });

        add(gridMedicos, new HorizontalLayout(nombreMedicoField, edadMedicoField, idMedicoField, telefonoMedicoField, especialidadMedicoField, horarioMedicoField),
                new HorizontalLayout(addMedicoButton, updateMedicoButton, deleteMedicoButton));
    }

    private byte parseByte(String s, byte defaultValue) {
        try {
            return Byte.parseByte(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void setupCitas() {
        add(new H2("Citas"));

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
                refreshGrids();
                binderCita.readBean(new Cita());
            }
        });

        Button updateCitaButton = new Button("Actualizar Cita", e ->{
            Cita selectedCita = gridCitas.asSingleSelect().getValue();
            if(selectedCita != null && binderCita.writeBeanIfValid(selectedCita)){
                hospitalService.guardarCita(selectedCita);
                refreshGrids();
            }
        });

        Button deleteCitaButton = new Button("Eliminar", e ->{
            Cita selectedCita = gridCitas.asSingleSelect().getValue();
            if(selectedCita!= null){
                hospitalService.eliminarCita(selectedCita.getId());
                refreshGrids();
            }
        });



        //Buscador por fecha
        DatePicker fechaBusqueda = new DatePicker("Buscar por Fecha");
        Button buscarCitasButton = new Button("Buscar", e -> {
            List<Cita> citasEnFecha = hospitalService.obtenerCitasPorFecha(fechaBusqueda.getValue());
            gridCitas.setItems(citasEnFecha);
        });
        Button mostrarTodasCitasButton = new Button("Mostrar todas", e -> refreshGrids());


        gridCitas.asSingleSelect().addValueChangeListener(event -> {
            binderCita.setBean(event.getValue());  //Carga los datos en los campos.
        });

        add(gridCitas, new HorizontalLayout(fechaCitaField, horaCitaField, motivoCitaField, pacienteComboBox, medicoComboBox),
                new HorizontalLayout(addCitaButton,updateCitaButton, deleteCitaButton), new HorizontalLayout(fechaBusqueda, buscarCitasButton, mostrarTodasCitasButton));
    }


    private void mostrarPacientesPalindromos() {
        gridPacientes.setItems(hospitalService.obtenerPacientesPalindromos());
    }

    private void mostrarPacientesVocalesIguales() {
        gridPacientes.setItems(hospitalService.obtenerPacientesConDosVocalesIguales());
    }


    private void refreshGrids() {
        gridPacientes.setItems(hospitalService.obtenerTodosLosPacientes());
        gridMedicos.setItems(hospitalService.obtenerTodosLosMedicos());
        gridCitas.setItems(hospitalService.obtenerTodasLasCitas());

    }
}