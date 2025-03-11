package com.unilabs.gestionhospital.Clases.PersonaDerivados;

public class Medico extends Persona {
    private String especialidad;
    private String horario;

    public Medico(String nombre, byte edad, String ID, String telefono, String especialidad, String horario){
        super(nombre, edad, ID, telefono);
        this.especialidad = especialidad;
        this.horario = horario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "especialidad='" + especialidad + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}
