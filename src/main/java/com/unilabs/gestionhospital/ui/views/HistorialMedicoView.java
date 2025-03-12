package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.HistorialMedico;
import com.unilabs.gestionhospital.model.Medicamento;
import com.unilabs.gestionhospital.model.Paciente;
import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("historial") // ruta de esta vista
@PageTitle("Historial Médico")
public class HistorialMedicoView extends VerticalLayout {

    private final HospitalService hospitalService;
    private final ComboBox<Paciente> pacienteComboBox;
    private final TextArea enfermedadesArea;
    private final Grid<Medicamento> medicamentosGrid;
    private final Binder<HistorialMedico> binder;

    private final TextField nombreMedicamentoField;
    private final TextField descripcionMedicamentoField;
    private final TextField tipoMedicamentoField;
    private final TextField dosisMedicamentoField;
    private final TextField frecuenciaMedicamentoField;
    private final TextField duracionMedicamentoField;
    private final DatePicker fechaInicioMedicamentoField;
    private final DatePicker fechaFinMedicamentoField;
    private final Button addMedicamentoButton;

    public HistorialMedicoView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;

        add(new H2("Historial Médico del Paciente"));

        pacienteComboBox = new ComboBox<>("Seleccionar Paciente");
        pacienteComboBox.setItems(hospitalService.obtenerTodosLosPacientes());
        pacienteComboBox.setItemLabelGenerator(Paciente::getNombre);


        enfermedadesArea = new TextArea("Enfermedades");
        enfermedadesArea.setWidthFull();


        medicamentosGrid = new Grid<>(Medicamento.class);
        medicamentosGrid.setColumns("nombre", "descripcion", "tipo", "dosis", "frecuencia", "duracion", "fechaInicio", "fechaFin");
        medicamentosGrid.addComponentColumn(this::createDeleteButton).setHeader("Acciones");


        // Formulario para añadir un nuevo medicamento
        nombreMedicamentoField = new TextField("Nombre");
        descripcionMedicamentoField = new TextField("Descripción");
        tipoMedicamentoField = new TextField("Tipo");
        dosisMedicamentoField = new TextField("Dosis");
        frecuenciaMedicamentoField = new TextField("Frecuencia");
        duracionMedicamentoField = new TextField("Duración");
        fechaInicioMedicamentoField = new DatePicker("Fecha Inicio");
        fechaFinMedicamentoField = new DatePicker("Fecha Fin");

        addMedicamentoButton = new Button("Añadir Medicamento", e -> agregarMedicamentoALista());


        binder = new Binder<>(HistorialMedico.class);
        binder.bind(enfermedadesArea,
                historial -> String.join(", ", historial.getEnfermedades()),
                (historial, value) -> historial.setEnfermedades(new ArrayList<>(List.of(value.split("\\s*,\\s*")))));


        Button cargarHistorialButton = new Button("Cargar Historial", e -> cargarHistorial());
        Button guardarHistorialButton = new Button("Guardar Historial", e -> guardarHistorial());

        pacienteComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                cargarHistorial();
            } else {
                binder.setBean(null);
                medicamentosGrid.setItems(new ArrayList<>());
            }
        });

        HorizontalLayout formLayout = new HorizontalLayout(nombreMedicamentoField, descripcionMedicamentoField, tipoMedicamentoField, dosisMedicamentoField, frecuenciaMedicamentoField, duracionMedicamentoField, fechaInicioMedicamentoField, fechaFinMedicamentoField, addMedicamentoButton);
        formLayout.setAlignItems(Alignment.END);

        add(pacienteComboBox, cargarHistorialButton, enfermedadesArea, formLayout, medicamentosGrid, guardarHistorialButton);
    }

    private Button createDeleteButton(Medicamento medicamento) {
        return new Button("Eliminar", e -> eliminarMedicamento(medicamento));
    }

    private void eliminarMedicamento(Medicamento medicamento) {
        HistorialMedico historial = binder.getBean();
        if (historial != null && historial.getMedicamentos().contains(medicamento)) {
            //Usa el servicio para eliminar correctamente
            hospitalService.eliminarMedicamentoDeHistorial(historial.getId(), medicamento.getId());
            cargarHistorial(); // Recarga el historial
            Notification.show("Medicamento eliminado correctamente.", 3000, Notification.Position.BOTTOM_CENTER);

        } else {
            Notification.show("Error: No se pudo eliminar el medicamento.", 3000, Notification.Position.BOTTOM_CENTER);
        }
    }

    @Transactional
    protected void cargarHistorial() {
        Paciente pacienteSeleccionado = pacienteComboBox.getValue();
        if (pacienteSeleccionado != null) {
            HistorialMedico historial = hospitalService.obtenerHistorialMedicoPorPaciente(pacienteSeleccionado);
            if (historial != null) {
                binder.setBean(historial);
                medicamentosGrid.setItems(historial.getMedicamentos());
            } else {
                HistorialMedico nuevoHistorial = new HistorialMedico();
                nuevoHistorial.setPaciente(pacienteSeleccionado);
                nuevoHistorial.setEnfermedades(new ArrayList<>());
                nuevoHistorial.setMedicamentos(new ArrayList<>());
                binder.setBean(nuevoHistorial);
                medicamentosGrid.setItems(new ArrayList<>());
            }
        }
    }

    private void agregarMedicamentoALista() {
        try {
            HistorialMedico historial = binder.getBean();
            if (historial == null) {
                historial = new HistorialMedico();
                historial.setPaciente(pacienteComboBox.getValue());
                historial.setEnfermedades(new ArrayList<>());
                historial.setMedicamentos(new ArrayList<>());
                binder.setBean(historial);
            }

            List<Medicamento> medicamentosActuales = getMedicamentosActuales(historial);
            historial.setMedicamentos(medicamentosActuales);
            medicamentosGrid.setItems(medicamentosActuales);

            limpiarCamposMedicamento();

        } catch (Exception e) {
            Notification.show("Error al agregar medicamento: " + e.getMessage(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }

    private List<Medicamento> getMedicamentosActuales(HistorialMedico historial) {
        Medicamento nuevoMedicamento = new Medicamento(
                nombreMedicamentoField.getValue(),
                descripcionMedicamentoField.getValue(),
                tipoMedicamentoField.getValue(),
                dosisMedicamentoField.getValue(),
                frecuenciaMedicamentoField.getValue(),
                duracionMedicamentoField.getValue(),
                fechaInicioMedicamentoField.getValue(),
                fechaFinMedicamentoField.getValue(),
                historial // Pasa el historial actual
        );
        List<Medicamento> medicamentosActuales = new ArrayList<>(historial.getMedicamentos());
        medicamentosActuales.add(nuevoMedicamento);
        return medicamentosActuales;
    }

    private void limpiarCamposMedicamento() {
        nombreMedicamentoField.clear();
        descripcionMedicamentoField.clear();
        tipoMedicamentoField.clear();
        dosisMedicamentoField.clear();
        frecuenciaMedicamentoField.clear();
        duracionMedicamentoField.clear();
        fechaInicioMedicamentoField.clear();
        fechaFinMedicamentoField.clear();
    }


    private void guardarHistorial() {
        Paciente paciente = pacienteComboBox.getValue();
        if (paciente == null) {
            Notification.show("Por favor, seleccione un paciente.", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (binder.validate().isOk()) {
            HistorialMedico historial = binder.getBean();
            if (historial == null) {
                historial = new HistorialMedico();
                historial.setPaciente(paciente);
                historial.setEnfermedades(new ArrayList<>());
                historial.setMedicamentos(new ArrayList<>());
            }
            // Obtener los ítems del DataProvider del grid (desde Vaadin 14 no se puede usar getItems()), usando streams
            // se usa streams para que quede compacto
            List<Medicamento> medicamentos = medicamentosGrid.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>())
                    .collect(Collectors.toList());
            historial.setMedicamentos(medicamentos);
            historial.setPaciente(paciente);  //Siempre asigna el paciente, exista o no
            hospitalService.guardarHistorialMedico(historial); //Usar el servicio
            Notification.show("Historial médico guardado.", 3000, Notification.Position.MIDDLE);
            cargarHistorial(); // Recarga el historial, para mostrar nuevos cambios.
        } else {
            Notification.show("Por favor, corrija los errores.", 3000, Notification.Position.MIDDLE);
        }
    }
}