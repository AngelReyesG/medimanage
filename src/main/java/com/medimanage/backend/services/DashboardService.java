package com.medimanage.backend.services;

import com.medimanage.backend.dtos.DashboardStatsDTO;
import com.medimanage.backend.repositories.CitaRepository;
import com.medimanage.backend.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DashboardService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public DashboardStatsDTO getStats() {
        LocalDate hoy = LocalDate.now();

        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(LocalTime.MAX);
        long citasHoy = citaRepository.countByFechaHoraBetween(inicioDia, finDia);

        long pacientesNuevos = pacienteRepository.countByFechaRegistroBetween(inicioDia, finDia);

        return new DashboardStatsDTO(citasHoy, pacientesNuevos);
    }
}
