package com.medimanage.backend.services;

import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.repositories.CitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    //Inyeccion por contructor
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    //Agendar una nueva cita con validación
    public Cita agendarCita(Cita cita) {
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede agendar una cita en una fecha u hora pasada.");
        }
        //Asignar estado inicial de forma segura
        if (cita.getEstado() == null) {
            cita.setEstado(Cita.EstadoCita.PENDIENTE);
        }
        return citaRepository.save(cita);
    }

    //Obtener todas las citas
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    //Buscar cita por ID
    public Optional<Cita> obtenerPorId(long id) {
        return citaRepository.findById(id);
    }

    //Obtener agenda completa de un médico
    public List<Cita> obtenerCitasPorMedico(Long idUsuario) {
        return citaRepository.findByMedicoIdUsuario(idUsuario);
    }

    //Pbtener el historial clínico de citas de un paciente
    public List<Cita> obtenerHistorialPaciente(Long idPaciente) {
        return citaRepository.findByPacienteIdPaciente(idPaciente);
    }

    //Cancelar una cita
    public Cita cancelarCita(Long id) {
        return citaRepository.findById(id).map(cita -> {
            cita.setEstado(Cita.EstadoCita.CANCELADA);
            return citaRepository.save(cita);
        }).orElseThrow(() -> new IllegalArgumentException("La cita con el ID especificado no existe."));
    }
}
