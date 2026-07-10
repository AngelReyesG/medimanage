package com.medimanage.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medimanage.backend.entities.Paciente;
import jakarta.persistence.*;
import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "notas_evolucion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaEvolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Paciente paciente;

    @Column(name = "fehca_consulta", nullable = false, updatable = false)
    private LocalDateTime fechaConsulta;

    // ---Signos Vitales---
    private String tensionArterial;
    private Double frecuenciaCardiaca;
    private Double temperatura;
    private Double peso;
    private Double talla;

    // ---Campos Clínicos--
    @Column(columnDefinition = "TEXT", nullable = false)
    private String motivoConsulta;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String exploracionFisica;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String planTratamiento;

    @PrePersist
    protected void onCreate() {
        this.fechaConsulta = LocalDateTime.now();
    }




}
