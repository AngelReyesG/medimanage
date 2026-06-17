package com.medimanage.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String historialClinico;
}
