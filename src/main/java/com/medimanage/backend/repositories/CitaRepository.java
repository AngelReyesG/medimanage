package com.medimanage.backend.repositories;

import com.medimanage.backend.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    //Mostrar al medico su agenda del día
    List<Cita> findByMedicoIdUsuario(Long idUsuario);

    //Ver historial de citas de un paciente
    List<Cita> findByPacienteIdPaciente(Long idPaciente);
}
