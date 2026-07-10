package com.medimanage.backend.controllers;

import com.medimanage.backend.entities.HistoriaClinica;
import com.medimanage.backend.services.HistoriaClinicaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historias-clinicas")
@CrossOrigin(origins = "http://localhost:51733", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class HistoriaClinicaController {

    private final HistoriaClinicaService historiaClinicaService;

    public HistoriaClinicaController(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    @GetMapping("/pacientes/{pacienteId}")
    public ResponseEntity<HistoriaClinica> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historiaClinicaService.obtenerPorPacienteId(pacienteId));
    }

    @PutMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> guardarOActualizar(
             @PathVariable Long pacienteId,
             @RequestBody HistoriaClinica historiaClinca) {
        return ResponseEntity.ok(historiaClinicaService.guardarOActualizar(pacienteId, historiaClinca));
    }
}
