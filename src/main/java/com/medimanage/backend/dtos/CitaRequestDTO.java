package com.medimanage.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaRequestDTO {

    private Long usuarioId;

    @NotNull(message = "El ID del paciente es obligatorio.")
    private Long pacienteId;

    @NotNull(message = "La fecha y hora de la cita son obligatorias.")
    @Future(message = "La fecha y hora de la cita deben ser en el futuro.")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss   ")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo de la cita es obligatorio.")
    @Size(min = 5, max = 255, message = "El motivio debe tener entre 5 y 255 caracteres")
    private String motivo;
}
