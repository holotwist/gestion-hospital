package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.model.Configurador;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route("configuracion") // ruta a esta vista
@PageTitle("Configuración")
public class ConfiguracionView extends VerticalLayout {

    private TextField horarioField;
    private TextField maxPacientesField;
    private ComboBox<String> reglasFacturacionField;
    private Binder<Configurador> binder;

    public ConfiguracionView() {
        add(new H2("Configuración del Hospital"));

        horarioField = new TextField("Horario de Atención");
        maxPacientesField = new TextField("Máximo Pacientes por Médico");
        reglasFacturacionField = new ComboBox<>("Reglas de Facturación");

        List<String> opcionesFacturacion = Arrays.asList(
                "Pago estándar por consulta",
                "Pago por suscripción mensual",
                "Pago por procedimiento",
                "Pago con seguro médico"
        );
        reglasFacturacionField.setItems(opcionesFacturacion);

        binder = new Binder<>(Configurador.class);
        binder.bind(horarioField, Configurador::getHorarioAtencion, Configurador::setHorarioAtencion);
        binder.bind(maxPacientesField,
                configurador -> String.valueOf(configurador.getMaxPacientesPorMedico()),
                (configurador, value) -> {
                    try {
                        configurador.setMaxPacientesPorMedico(Integer.parseInt(value));
                    } catch (NumberFormatException e) {
                        // Manejar el error si no es un número válido
                        Notification.show("Ingrese un número válido para el máximo de pacientes.", 3000, Notification.Position.MIDDLE);
                    }
                });
        binder.bind(reglasFacturacionField, Configurador::getReglasFacturacion, Configurador::setReglasFacturacion);


        Button guardarButton = new Button("Guardar", e -> guardarConfiguracion());

        // Cargar la configuración actual
        cargarConfiguracion();

        add(horarioField, maxPacientesField, reglasFacturacionField, guardarButton);
        setAlignItems(Alignment.START); // Alinea los componentes a la izquierda
    }
    private void cargarConfiguracion() {
        binder.setBean(Configurador.getInstance()); // Carga los valores actuales
    }

    private void guardarConfiguracion() {
        if (binder.validate().isOk()) {
            //binder.setBean() facilita actualizar la instancia, evitando mucho código repetitivo
            Notification.show("Configuración guardada.", 3000, Notification.Position.MIDDLE);
        } else {
            Notification.show("Por favor, corrija los errores en el formulario.", 3000, Notification.Position.MIDDLE);
        }
    }
}