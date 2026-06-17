package com.medimanage.backend.controllers;

import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.dtos.PacienteRequestDTO;
import com.medimanage.backend.services.PacienteService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    //Inyección por constructor
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //Registrar nuevo paciente
    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody PacienteRequestDTO dto) {
        Paciente nuevoPaciente = pacienteService.registrarPaciente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    //Buscar paciente por nombre
    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerPacientes(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            return ResponseEntity.ok(pacienteService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    //Busca paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        return pacienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Actualizar un paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, @RequestBody PacienteRequestDTO dto) {
        return pacienteService.actualizarPaciente(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        if (pacienteService.eliminarPaciente(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
