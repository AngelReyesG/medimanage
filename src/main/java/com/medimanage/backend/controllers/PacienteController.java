package com.medimanage.backend.controllers;

import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.dtos.PacienteRequestDTO;
import com.medimanage.backend.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Paciente> registrarPaciente(@Valid @RequestBody PacienteRequestDTO dto, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Paciente paciente = new Paciente();

        paciente.setNombre(dto.getNombre());
        paciente.setApellidos(dto.getApellidos());
        paciente.setTelefono(dto.getTelefono());
        paciente.setCorreo(dto.getCorreo());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());

        paciente.setUsuario(usuarioAutenticado);
        Paciente nuevoPaciente = pacienteService.registrarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    //Buscar paciente por nombre
    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerPacientes(
            @RequestParam(value = "nombre", required = false, defaultValue = "") String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            return ResponseEntity.ok(pacienteService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    //Busca paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {

        Paciente paciente = pacienteService.obtenerPorId(id);

        return ResponseEntity.ok(paciente);
    }

    //Actualizar un paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@Valid @PathVariable Long id, @RequestBody PacienteRequestDTO dto) {

        Paciente datosActualizados = new Paciente();
        datosActualizados.setNombre(dto.getNombre());
        datosActualizados.setApellidos(dto.getApellidos());
        datosActualizados.setTelefono(dto.getTelefono());
        datosActualizados.setCorreo(dto.getCorreo());
        datosActualizados.setFechaNacimiento(dto.getFechaNacimiento());
        datosActualizados.setNotasAlergias(dto.getNotasAlergias());
        datosActualizados.setHistorialClinico(dto.getHistorialClinico());

        Paciente pacienteModificado = pacienteService.actualizarPaciente(id, datosActualizados);
        return ResponseEntity.ok(pacienteModificado);
    }

    //Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
