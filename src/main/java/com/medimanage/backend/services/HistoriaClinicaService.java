package com.medimanage.backend.services;

import com.medimanage.backend.entities.HistoriaClinica;
import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.repositories.HistoriaClinicaRepository;
import com.medimanage.backend.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final PacienteRepository pacienteRepository;

    public HistoriaClinicaService(HistoriaClinicaRepository historiaClinicaRepository, PacienteRepository pacienteRepository) {
        this.historiaClinicaRepository = historiaClinicaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional(readOnly = true)
    public HistoriaClinica obtenerPorPacienteId(Long idPaciente) {
        return historiaClinicaRepository.findByPacienteIdPaciente(idPaciente)
                .orElseGet(() -> {
                    HistoriaClinica nuevaHistoria = new HistoriaClinica();
                    Paciente paciente = pacienteRepository.findById(idPaciente)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));
                    nuevaHistoria.setPaciente(paciente);
                    return nuevaHistoria;
                });
    }

    @Transactional
    public HistoriaClinica guardarOActualizar(Long idPaciente, HistoriaClinica datosNuevos) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));

        HistoriaClinica historia = historiaClinicaRepository.findByPacienteIdPaciente(idPaciente)
                .orElse(new HistoriaClinica());

        historia.setPaciente(paciente);
        historia.setAntecedentesFamiliares(datosNuevos.getAntecedentesFamiliares());
        historia.setAntecedentesPatologicos(datosNuevos.getAntecedentesPatologicos());
        historia.setAntecedentesNoPatologicos(datosNuevos.getAntecedentesNoPatologicos());

        return historiaClinicaRepository.save(historia);
    }
}
