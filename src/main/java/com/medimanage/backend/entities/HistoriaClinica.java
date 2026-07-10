package com.medimanage.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "historias_clinicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistoria;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    @JsonBackReference
    private Paciente paciente;

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "antecedentes_patologicos", columnDefinition = "TEXT")
    private String antecedentesPatologicos;

    @Column(name = "antecedentes_no_patologicos", columnDefinition = "TEXT")
    private String antecedentesNoPatologicos;

    @Column(name = "fecha_ultima_actualizacion")
    private LocalDateTime fechaUltimaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }
}
