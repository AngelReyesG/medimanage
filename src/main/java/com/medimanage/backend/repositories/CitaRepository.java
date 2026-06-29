package com.medimanage.backend.repositories;

import com.medimanage.backend.entities.Cita;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    //Mostrar al medico su agenda del día
    @Query("SELECT c FROM Cita c WHERE c.usuario.idUsuario = :idUsuario")
    List<Cita> findByIdUsuario(@Param("idUsuario") Long idUsuario);

    //Ver historial de citas de un paciente
    @Query("SELECT c FROM Cita c WHERE c.paciente.idPaciente = :idPaciente")
    List<Cita> findByIdPaciente(@Param("idPaciente") Long idPaciente);

    List<Cita> findByUsuario(Usuario usuario);

    boolean existsByUsuarioAndFechaHoraAndEstadoNot(Usuario usuario, LocalDateTime fechaHora, EstadoCita estado);

}
