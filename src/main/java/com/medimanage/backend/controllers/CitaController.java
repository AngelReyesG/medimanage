package com.medimanage.backend.controllers;

import com.medimanage.backend.dtos.CitaRequestDTO;
import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.services.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    //Obtener todas las citas o filtrar por médico
    @GetMapping
    public ResponseEntity<List<Cita>> listarCitas(@RequestParam(required = false) Long idUsuario) {
        if (idUsuario != null) {
            return ResponseEntity.ok(citaService.obtenerCitasPorMedico(idUsuario));
        }
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    //Agendar una nueva cita
    @PostMapping ("/registrar")
    public ResponseEntity<?> agendarCita(@Valid @RequestBody CitaRequestDTO dto) {
        try {
            Cita nuevaCita = citaService.agendarCita(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Actualizar el estado de la cita (PENDIENTE -> COMPLETADA / CANCELADA)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Cita> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return citaService.cambiarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener historial de un paciente específico
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.obtenerHistorialPaciente(pacienteId));
    }

    //Eliminiación lógica de una cita por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        if (citaService.cancelarCita(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
