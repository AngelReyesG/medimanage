package com.medimanage.backend.controllers;

import com.medimanage.backend.entities.NotaEvolucion;
import com.medimanage.backend.services.NotaEvolucionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notas-evolucion")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
public class NotaEvolucionController {

    private final NotaEvolucionService notaEvolucionService;

    public NotaEvolucionController(NotaEvolucionService notaEvolucionService) {
        this.notaEvolucionService = notaEvolucionService;
    }

    //Obtener historial de notas por paciente
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<NotaEvolucion>> obtenerHistorialPorPaciente(@PathVariable Long idPaciente) {
        return ResponseEntity.ok(notaEvolucionService.listarPorPaciente(idPaciente));
    }

    //Crear nueva nota de evolución vinculada al paciente
    @PostMapping("/paciente/{idPaciente}")
    public ResponseEntity<NotaEvolucion> crearNota(
            @PathVariable Long idPaciente,
            @RequestBody NotaEvolucion nota) {
        return ResponseEntity.ok(notaEvolucionService.registrarNota(idPaciente, nota));
    }
}
