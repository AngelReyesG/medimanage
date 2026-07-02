package com.medimanage.backend.repositories;

import com.medimanage.backend.entities.Paciente;
import com.medimanage.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    //Metodo personalizado para buscar pacientes por nombre
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);

    //Metodo personalizado para buscar pacientes por medico
    List<Paciente> findByUsuario(Usuario usuario);

    long countByFechaRegistroBetween(LocalDateTime inicio, LocalDateTime fin);
}
