package com.medimanage.backend.services;

import com.medimanage.backend.dtos.PacienteRequestDTO;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.repositories.UsuarioRepository;
import com.medimanage.backend.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Inyeccion de dependencias por constructor
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    //Registrar nuevo paciente
    public Paciente registrarPaciente(Paciente paciente) {
        Usuario medicoLogueado = obtenerMedicoAutenticado();

        paciente.setUsuario(medicoLogueado);

        return pacienteRepository.save(paciente);
    }

    //Actualizar datos de paciente
    public Paciente actualizarPaciente(Long id, Paciente datosActualizados) {
        Usuario medicoLogueado = obtenerMedicoAutenticado();

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con el ID: " + id));

        if(!pacienteExistente.getUsuario().getIdUsuario().equals(medicoLogueado.getIdUsuario())) {
            throw new RuntimeException("No se cuenta con permisos para modificar la información de este paciente.");
        }
            pacienteExistente.setNombre(datosActualizados.getNombre());
            pacienteExistente.setApellidos(datosActualizados.getApellidos());
            pacienteExistente.setTelefono(datosActualizados.getTelefono());
            pacienteExistente.setCorreo(datosActualizados.getCorreo());
            pacienteExistente.setNotasAlergias(datosActualizados.getNotasAlergias());
            pacienteExistente.setHistorialClinico(datosActualizados.getHistorialClinico());

            return pacienteRepository.save(pacienteExistente);
    }

    //Obtener todos los pacientes
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    //Obtener paciente por médico
    public List<Paciente> obtenerPorMedico() {
        Usuario medicoLogueado = obtenerMedicoAutenticado();

        return pacienteRepository.findByUsuario(medicoLogueado);
    }
    //Buscar paciente por ID
    public Paciente obtenerPorId(Long id) {
        Usuario medicoLogueado = obtenerMedicoAutenticado();

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con el ID: " + id));

        if (!paciente.getUsuario().getIdUsuario().equals(medicoLogueado.getIdUsuario())) {
            throw new RuntimeException("No se cuenta con permisos para ver la información de este paciente.");
        }
        return paciente;
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

    //Obtener el médico autenticado desde el token JWT
    private Usuario obtenerMedicoAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado en el sistema."));
    }

}
