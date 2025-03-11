package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.Paciente;
import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("pacientes") // Ruta para esta vista
@PageTitle("Pacientes")
public class PacientesView extends VerticalLayout {

    private final HospitalService hospitalService;
    private final Grid<Paciente> gridPacientes = new Grid<>(Paciente.class);
    private final Binder<Paciente> binderPaciente = new Binder<>(Paciente.class);

    public PacientesView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;

        add(new H2("Gestión de Pacientes"));
        setupPacientes();
        refreshGrid();
    }

    private void setupPacientes() {
        gridPacientes.setColumns("nombre", "edad", "id", "telefono");
        gridPacientes.addComponentColumn(this::createCloneButton).setHeader("Clonar");

        TextField nombrePacienteField = new TextField("Nombre");
        TextField edadPacienteField = new TextField("Edad");
        TextField idPacienteField = new TextField("ID");
        TextField telefonoPacienteField = new TextField("Teléfono");

        binderPaciente.forField(nombrePacienteField).bind(Paciente::getNombre, Paciente::setNombre);
        binderPaciente.forField(edadPacienteField).bind(p -> String.valueOf(p.getEdad()), (p, s) -> p.setEdad(parseByte(s, (byte) 0)));
        binderPaciente.forField(idPacienteField).bind(Paciente::getIdentificacion, Paciente::setIdentificacion);
        binderPaciente.forField(telefonoPacienteField).bind(Paciente::getTelefono, Paciente::setTelefono);

        Button addPacienteButton = new Button("Añadir Paciente", e -> {
            Paciente paciente = new Paciente();
            if (binderPaciente.writeBeanIfValid(paciente)) {
                hospitalService.guardarPaciente(paciente);
                refreshGrid();
                binderPaciente.readBean(new Paciente()); // Limpiar el form
            }
        });

        Button updatePacienteButton = new Button("Actualizar", e -> {
            Paciente selectedPaciente = gridPacientes.asSingleSelect().getValue();
            if (selectedPaciente != null && binderPaciente.writeBeanIfValid(selectedPaciente)) {
                hospitalService.guardarPaciente(selectedPaciente);
                refreshGrid();
            }
        });

        Button deletePacienteButton = new Button("Eliminar", e -> {
            Paciente selectedPaciente = gridPacientes.asSingleSelect().getValue();
            if (selectedPaciente != null) {
                hospitalService.eliminarPaciente(selectedPaciente.getId());
                refreshGrid();
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
                refreshGrid();
                Notification.show("Paciente clonado con éxito.", 3000, Notification.Position.BOTTOM_CENTER);
            } catch (CloneNotSupportedException ex) {
                Notification.show("Error al clonar paciente: " + ex.getMessage(), 5000, Notification.Position.BOTTOM_CENTER);
            }
        });
    }

    private void refreshGrid() {
        gridPacientes.setItems(hospitalService.obtenerTodosLosPacientes());
    }

    private byte parseByte(String s, byte defaultValue) {
        try {
            return Byte.parseByte(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}