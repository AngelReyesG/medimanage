package com.medimanage.backend.services;

import com.medimanage.backend.entities.NotaEvolucion;
import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.repositories.NotaEvolucionRepository;
import com.medimanage.backend.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NotaEvolucionService {

    private final NotaEvolucionRepository notaEvolucionRepository;
    private final PacienteRepository pacienteRepository;

    public NotaEvolucionService(NotaEvolucionRepository notaEvolucionRepository, PacienteRepository pacienteRepository) {
        this.notaEvolucionRepository = notaEvolucionRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional(readOnly = true)
    public List<NotaEvolucion> listarPorPaciente(Long idPaciente) {
        return notaEvolucionRepository.findByPacienteIdPacienteOrderByFechaConsultaDesc(idPaciente);
    }

    @Transactional
    public NotaEvolucion registrarNota(Long idPaciente, NotaEvolucion nuevaNota) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + idPaciente));

        nuevaNota.setPaciente(paciente);
        return notaEvolucionRepository.save(nuevaNota);
    }
}
