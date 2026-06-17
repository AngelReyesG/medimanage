package com.medimanage.backend.services;

import com.medimanage.backend.dtos.PacienteRequestDTO;
import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private final PacienteRepository pacienteRepository;

    //Inyeccion de dependencias por constructor
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    //Registrar nuevo paciente
    public Paciente registrarPaciente(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente ();
        paciente.setNombre(dto.getNombre());
        paciente.setApellidos(dto.getApellidos());
        paciente.setTelefono(dto.getTelefono());
        paciente.setCorreo(dto.getCorreo());
        paciente.setHistorialClinico(dto.getHistorialClinico());
        return pacienteRepository.save(paciente);
    }

    //Actualizar datos de paciente
    public Optional<Paciente> actualizarPaciente(Long id, PacienteRequestDTO dto) {
        return pacienteRepository.findById(id).map(pacienteExistente -> {
            pacienteExistente.setNombre(dto.getNombre());
            pacienteExistente.setApellidos(dto.getApellidos());
            pacienteExistente.setTelefono(dto.getTelefono());
            pacienteExistente.setCorreo(dto.getCorreo());
            pacienteExistente.setHistorialClinico(dto.getHistorialClinico());
            return pacienteRepository.save(pacienteExistente);
        });
    }

    //Obtener todos los pacientes
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    //Buscar paciente por ID
    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    //Buscar paciente por nombre
    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    //Eliminar paciente
    public boolean eliminarPaciente(Long id) {

        return pacienteRepository.findById(id).map(paciente -> {
            pacienteRepository.delete(paciente);
            return true;
        }).orElse(false);
    }
}
