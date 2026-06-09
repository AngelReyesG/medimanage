package com.medimanage.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.MEDICO;

    @Column(name = "fecha_creacion", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    //Definicion de ENUM interno para manejar los roles de forma segura
    public enum Rol {
        MEDICO,
        ASISTENTE,
        ADMIN
    }
}
