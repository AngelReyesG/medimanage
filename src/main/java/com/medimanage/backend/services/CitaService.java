package com.medimanage.backend.services;

import com.medimanage.backend.dtos.CitaRequestDTO;
import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.enums.EstadoCita;
import com.medimanage.backend.repositories.CitaRepository;
import com.medimanage.backend.repositories.PacienteRepository;
import com.medimanage.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    //Obtener médico autenticado desde token
    private Usuario obtenerMedicoAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Médico no autenticado en la sesión."));
    }
    //Obtener todas las citas
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll().stream()
                .filter(cita -> !cita.getEstado().equals("ELIMINADA"))
                .collect(Collectors.toList());
    }

    //Obtener agenda completa de un médico
    public List<Cita> obtenerCitasPorMedico() {
        Usuario medicoLogueado = obtenerMedicoAutenticado();

        return citaRepository.findByUsuario(medicoLogueado).stream()
                .filter(cita -> !cita.getEstado().equals(EstadoCita.CANCELADA))
                .collect(Collectors.toList());
    }

    //Buscar cita por ID
    public Cita obtenerPorId(Long id) {
        Usuario medicoLogueado = obtenerMedicoAutenticado();

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        if (!cita.getUsuario().getIdUsuario().equals(medicoLogueado.getIdUsuario())) {
            throw new RuntimeException("No cuenta con permisos para ver esta cita.");
        }
        return cita;
    }

    //Agendar una nueva cita con validación
    public Cita agendarCita(CitaRequestDTO dto) {
       Usuario medicoLogueado = obtenerMedicoAutenticado();

       Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
               .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));

       if (!paciente.getUsuario().getIdUsuario().equals(medicoLogueado.getIdUsuario())) {
           throw new RuntimeException("No puedes agendar citas a un paciente que no está registrado en tu consultorio.");
       }

       boolean horarioOcupado = citaRepository.existsByUsuarioAndFechaHoraAndEstadoNot(
               medicoLogueado, dto.getFechaHora(), EstadoCita.CANCELADA);

       if (horarioOcupado) {
           throw new RuntimeException("Ya cuentas con una cita programada para la fecha y hora seleccionada.");
       }

       Cita cita = new Cita();
       cita.setUsuario(medicoLogueado);
       cita.setPaciente(paciente);
       cita.setFechaHora(dto.getFechaHora());
       cita.setMotivo(dto.getMotivo());
       cita.setEstado(EstadoCita.PENDIENTE);

       return citaRepository.save(cita);
    }

    //Cambiar estado de una cita
    public Cita cambiarEstado(Long id, EstadoCita nuevoEstado) {
        Cita cita = obtenerPorId(id);
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }

    //Obtener el historial clínico de citas de un paciente
    public List<Cita> obtenerHistorialPaciente(Long idPaciente) {
        return citaRepository.findByIdPaciente(idPaciente).stream()
                .filter(cita ->!cita.getEstado().equals("ELIMINADA"))
                .collect(Collectors.toList());
    }

    //Cancelar una cita
    public void cancelarCita(Long id) {
        Cita cita = obtenerPorId(id);
        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);
    }
}
