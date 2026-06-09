package com.medimanage.backend.repositories;

import com.medimanage.backend.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    //Metodo personalizado para buscar pacientes por nombre
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
}
