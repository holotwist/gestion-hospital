package com.unilabs.gestionhospital.ui.views;

import com.unilabs.gestionhospital.service.HospitalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;

@Route("")
public class MainView extends AppLayout {

    private final HospitalService hospitalService;
    private final Div content = new Div(); // Contenedor principal para el contenido
    private Tabs tabs;
    private Map<Tab, Component> tabToView = new HashMap<>();

    public MainView(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        createHeader();
        createDrawer();
        setContent(content); // Establecer el contenedor principal una vez

        // Mostrar la vista de Pacientes por defecto al inicio
        showView(new PacientesView(hospitalService));
    }

    private void createHeader() {
        H1 logo = new H1("Clínicas Fernely");
        logo.addClassNames("text-l", "m-m");

        DrawerToggle toggle = new DrawerToggle();

        HorizontalLayout header = new HorizontalLayout(toggle, logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        // Crear Tabs y asociarlos a las vistas, pero ahora con botones
        Tab pacientesTab = createTab(new PacientesView(hospitalService), "Pacientes");
        Tab medicosTab = createTab(new MedicosView(hospitalService), "Médicos");
        Tab citasTab = createTab(new CitasView(hospitalService), "Citas");
        Tab configuracionTab = createTab(new ConfiguracionView(), "Configuración");
        Tab pacientesEspecialesTab = createTab(new PacientesEspecialesView(hospitalService), "Pacientes Especiales");

        tabs.add(pacientesTab, medicosTab, citasTab, configuracionTab, pacientesEspecialesTab);

        tabs.addSelectedChangeListener(event -> {
            Component selectedView = tabToView.get(tabs.getSelectedTab());
            showView(selectedView);
        });

        addToDrawer(tabs);
    }
    private Tab createTab(Component view, String label) {
        Button button = new Button(label);
        button.setWidthFull();
        Tab tab = new Tab(button);
        tabToView.put(tab, view);
        return tab;
    }
    private void showView(Component view) {
        content.removeAll(); // Limpiar el contenido anterior
        content.add(view);    // Agregar la nueva vista
    }
}