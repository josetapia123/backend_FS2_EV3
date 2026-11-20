package com.creativamente.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TareaRequest(
        @NotBlank(message = "El especialista es obligatorio") String especialista,
        @NotBlank(message = "La descripción de la tarea es obligatoria") String tarea,
        // Se acepta el código del proyecto (ej: "PR001") para mayor facilidad desde el frontend
        @NotBlank(message = "El código del proyecto es obligatorio") String proyectoCodigo,
        @NotBlank(message = "El estado es obligatorio") String estado,
        @NotBlank(message = "La prioridad es obligatoria") String prioridad,
        @NotNull(message = "Las horas estimadas son obligatorias")
        @Min(value = 1, message = "Las horas deben ser al menos 1") Integer horas
) {}