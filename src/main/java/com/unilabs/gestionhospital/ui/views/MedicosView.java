package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.Medico;
import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("medicos") // ruta de esta vista
@PageTitle("Medicos")
public class MedicosView extends VerticalLayout {

    private final HospitalService hospitalService;
    private final Grid<Medico> gridMedicos = new Grid<>(Medico.class);
    private final Binder<Medico> binderMedico = new Binder<>(Medico.class);

    public MedicosView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;

        add(new H2("Gestión de Médicos"));
        setupMedicos();
        refreshGrid();
    }
    private void setupMedicos() {
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
                refreshGrid();
                binderMedico.readBean(new Medico()); //Limpiar form
            }
        });

        Button updateMedicoButton = new Button("Actualizar", e -> {
            Medico selectedMedico = gridMedicos.asSingleSelect().getValue();
            if(selectedMedico != null && binderMedico.writeBeanIfValid(selectedMedico)){
                hospitalService.guardarMedico(selectedMedico);
                refreshGrid();
            }
        });

        Button deleteMedicoButton = new Button("Eliminar", e ->{
            Medico selectedMedico = gridMedicos.asSingleSelect().getValue();
            if (selectedMedico != null){
                hospitalService.eliminarMedico(selectedMedico.getId());
                refreshGrid();
            }
        });

        gridMedicos.asSingleSelect().addValueChangeListener(event ->{
            binderMedico.setBean(event.getValue());
        });

        add(gridMedicos, new HorizontalLayout(nombreMedicoField, edadMedicoField, idMedicoField, telefonoMedicoField, especialidadMedicoField, horarioMedicoField),
                new HorizontalLayout(addMedicoButton, updateMedicoButton, deleteMedicoButton));
    }

    private void refreshGrid() {
        gridMedicos.setItems(hospitalService.obtenerTodosLosMedicos());
    }
    private byte parseByte(String s, byte defaultValue) {
        try {
            return Byte.parseByte(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}