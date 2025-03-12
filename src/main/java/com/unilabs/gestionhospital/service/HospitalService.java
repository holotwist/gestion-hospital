package com.unilabs.gestionhospital.service;

import com.unilabs.gestionhospital.model.*;
import com.unilabs.gestionhospital.model.gestor.GestorPacientes;
import com.unilabs.gestionhospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    @Autowired
    private EnfermedadRepository enfermedadRepository;


    // CRUD Paciente
    @Transactional
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
    @Transactional(readOnly = true)
    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Paciente obtenerPaciente(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }
    @Transactional
    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }


    // CRUD Medico
    @Transactional
    public Medico guardarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }
    @Transactional(readOnly = true)
    public List<Medico> obtenerTodosLosMedicos() {
        return medicoRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Medico obtenerMedico(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }
    @Transactional
    public void eliminarMedico(Long id) {
        medicoRepository.deleteById(id);
    }


    // CRUD Cita
    @Transactional
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }
    @Transactional(readOnly = true)
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cita obtenerCita(Long id) {
        return citaRepository.findById(id).orElse(null);
    }
    @Transactional
    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }


    // CRUD HistorialMedico
    @Transactional
    public HistorialMedico guardarHistorialMedico(HistorialMedico historialMedico) {
        if (historialMedico.getEnfermedades() == null) {
            historialMedico.setEnfermedades(new ArrayList<>());
        }
        if(historialMedico.getMedicamentos() == null){
            historialMedico.setMedicamentos(new ArrayList<>());
        }

        return historialMedicoRepository.save(historialMedico);
    }

    @Transactional(readOnly = true)
    public HistorialMedico obtenerHistorialMedico(Long id) {
        return historialMedicoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public HistorialMedico obtenerHistorialMedicoPorPaciente(Paciente paciente) {
        return historialMedicoRepository.findByPaciente(paciente).orElse(null);
    }

    @Transactional
    public void eliminarHistorialMedico(Long id) {
        historialMedicoRepository.deleteById(id);
    }
    @Transactional
    public void eliminarMedicamentoDeHistorial(Long historialId, Long medicamentoId) {
        HistorialMedico historial = historialMedicoRepository.findById(historialId).orElse(null);
        if (historial != null) {
            Medicamento medicamento = medicamentoRepository.findById(medicamentoId).orElse(null);
            if (medicamento != null) {
                historial.getMedicamentos().remove(medicamento);
                historialMedicoRepository.save(historial); // Guarda los cambios
            }
        }
    }


    // GestorPacientes
    @Transactional(readOnly = true)
    public List<Paciente> obtenerPacientesPalindromos() {
        List<Paciente> todosLosPacientes = pacienteRepository.findAll();
        return GestorPacientes.PacientesPalindromos(todosLosPacientes);
    }

    @Transactional(readOnly = true)
    public List<Paciente> obtenerPacientesConDosVocalesIguales() {
        List<Paciente> todosLosPacientes = pacienteRepository.findAll();
        return GestorPacientes.PacientesVocalesIguales(todosLosPacientes);
    }
    @Transactional
    public Paciente clonarPaciente(Long id) throws CloneNotSupportedException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente pacienteOriginal = pacienteOptional.get();
            Paciente pacienteClonado = pacienteOriginal.clone();
            // Desvincula el clon de JPA, esto es importante para evitar modificar el original
            pacienteClonado.setId(null);  // Para que sea una nueva entidad al guardar, ver archivo /explain.md
            return pacienteClonado;

        } else {
            throw new CloneNotSupportedException("Paciente con ID " + id + " no encontrado.");
        }
    }
}