package com.medimanage.backend.controllers;

import com.medimanage.backend.dtos.CitaRequestDTO;
import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.enums.EstadoCita;
import com.medimanage.backend.services.CitaService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
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
    @PostMapping("/solicitar")
    public ResponseEntity<Cita> agendarCita(@Valid @RequestBody CitaRequestDTO dto) {
        Cita nuevaCita = citaService.agendarCita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    //Aprobar cita
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Cita> confirmarCita(@PathVariable("id") Long id) {
        Cita citaAprobada = citaService.confirmarCita(id);
        return ResponseEntity.ok(citaAprobada);
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

    //Obtener horarios disponibles para agendar
    @GetMapping("/horarios-disponibles")
    public ResponseEntity<List<String>> getHorariosDisponibles(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                                               @RequestParam("medicoId") Long medicoId) {

        List<String> horasLibres = citaService.calcularHorariosLibres(fecha, medicoId);
        return ResponseEntity.ok(horasLibres);
    }

    //Eliminiación lógica de una cita por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return ResponseEntity.noContent().build();
    }

    //Obtener solicitudes pendientes
    @GetMapping("/solicitudes-pendientes")
    public ResponseEntity<List<Cita>> obtenerSolicitudesPendientes() {
        List<Cita> todasLasCitas = citaService.obtenerCitasPorMedico();

        List<Cita> pendientes = todasLasCitas.stream()
                .filter(cita -> cita.getEstado() == EstadoCita.PENDIENTE)
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(pendientes);
    }

    //Obtener agenda diaria
    @GetMapping("/agenda-diaria")
    public ResponseEntity<List<Cita>> obtenerAgendaDiaria(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){

        List<Cita> todasLasCitas = citaService.obtenerCitasPorMedico();

        List<Cita> agendaDelDia = todasLasCitas.stream()
                .filter(cita -> cita.getEstado() == EstadoCita.CONFIRMADA)
                .filter(cita -> cita.getFechaHora().toLocalDate().equals(fecha))
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(agendaDelDia);
    }
}
