package com.medimanage.backend.controllers;

import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.services.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    //Agendar una nueva cita
    @PostMapping
    public ResponseEntity<?> agendarCita(@RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.agendarCita(cita);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Obtener todas las citas
    @GetMapping
    public ResponseEntity<List<Cita>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    //Obtener la agenda de un médico específico
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Cita>> obtenerPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(citaService.obtenerCitasPorMedico(medicoId));
    }

    //Obtener historial de un paciente específico
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.obtenerHistorialPaciente(pacienteId));
    }

    //Cancelación lógica de una cita por ID
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        try {
            Cita citaCancelada = citaService.cancelarCita(id);
            return ResponseEntity.ok(citaCancelada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
