package com.medimanage.backend.services;

import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    //Inyeccion de dependencias por constructor
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    //Registrar nuevo paciente
    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
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
    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }
}
