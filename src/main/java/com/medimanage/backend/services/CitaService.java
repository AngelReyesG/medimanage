package com.medimanage.backend.services;

import com.medimanage.backend.dtos.CitaRequestDTO;
import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.repositories.CitaRepository;
import com.medimanage.backend.repositories.PacienteRepository;
import com.medimanage.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    //Obtener todas las citas
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll().stream()
                .filter(cita -> !cita.getEstado().equals("ELIMINADA"))
                .collect(Collectors.toList());
    }

    //Obtener agenda completa de un médico
    public List<Cita> obtenerCitasPorMedico(Long idUsuario) {
        return citaRepository.findByIdUsuario(idUsuario).stream()
                .filter(cita -> !cita.getEstado().equals("ELIMINADA"))
                .collect(Collectors.toList());
    }

    //Buscar cita por ID
    public Optional<Cita> obtenerPorId(long id) {
        return citaRepository.findById(id)
                .filter(cita -> !cita.getEstado().equals("ELIMINADA"));
    }

    //Agendar una nueva cita con validación
    public Cita agendarCita(CitaRequestDTO dto) {
        //Validar que exista el médico
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + dto.getUsuarioId()));

        //Validar que exista el paciente
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));

        //Crear estructura de Cita
        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setPaciente(paciente);
        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado("PENDIENTE");

        return citaRepository.save(cita);
    }

    //Cambiar estado de una cita
    public Optional<Cita> cambiarEstado(Long id, String nuevoEstado) {
        return citaRepository.findById(id)
                .filter(cita -> !cita.getEstado().equals("ELIMINADA"))
                .map( cita -> {
                    cita.setEstado(nuevoEstado.toUpperCase());
                    return citaRepository.save(cita);
                });
    }

    //Obtener el historial clínico de citas de un paciente
    public List<Cita> obtenerHistorialPaciente(Long idPaciente) {
        return citaRepository.findByIdPaciente(idPaciente).stream()
                .filter(cita ->!cita.getEstado().equals("ELIMINADA"))
                .collect(Collectors.toList());
    }

    //Cancelar una cita
    public boolean cancelarCita(Long id) {
        return citaRepository.findById(id).map(cita -> {
            cita.setEstado("ELIMINADA");
            citaRepository.save(cita);
            return true;
        }).orElse(false);
    }
}
