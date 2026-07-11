package com.medimanage.backend.controllers;

import com.medimanage.backend.dtos.CitaRequestDTO;
import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.enums.EstadoCita;
import com.medimanage.backend.services.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE})
public class CitaController {

    @Autowired
    private CitaService citaService;

    //Obtener todas las citas o filtrar por médico
    @GetMapping
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(citaService.obtenerCitasPorMedico());
    }

    //Obtener citas por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    //Agendar una nueva cita
    @PostMapping
    public ResponseEntity<Cita> agendarCita(@Valid @RequestBody CitaRequestDTO dto) {
        Cita nuevaCita = citaService.agendarCita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    //Actualizar el estado de la cita
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Cita> cambiarEstado(@PathVariable Long id, @RequestParam EstadoCita nuevoEstado) {
        Cita citaModificada = citaService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(citaModificada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable Long id, @Valid @RequestBody CitaRequestDTO dto) {
        Cita citaActualizada = citaService.actualizarCita(id, dto);
        return ResponseEntity.ok(citaActualizada);
    }

    //Obtener historial de un paciente específico
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.obtenerHistorialPaciente(pacienteId));
    }

    //Eliminiación lógica de una cita por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return ResponseEntity.noContent().build();
    }
}
