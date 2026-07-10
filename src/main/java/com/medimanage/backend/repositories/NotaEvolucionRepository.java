package com.medimanage.backend.repositories;

import com.medimanage.backend.entities.NotaEvolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotaEvolucionRepository extends JpaRepository<NotaEvolucion, Long> {

    //Devuelte historial cronológico descendente
    List<NotaEvolucion> findByPacienteIdPacienteOrderByFechaConsultaDesc(Long idPaciente);
}
