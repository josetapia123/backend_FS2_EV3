package com.creativamente.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ProyectoRequest(
        @NotBlank(message = "El código es obligatorio") String codigo,
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El cliente es obligatorio") String cliente,
        @NotBlank(message = "El tipo es obligatorio") String tipo,
        @NotNull(message = "El presupuesto es obligatorio")
        @Min(value = 0, message = "El presupuesto no puede ser negativo") Long presupuesto,
        @NotNull(message = "La fecha de inicio es obligatoria") LocalDate inicio,
        @NotNull(message = "La fecha de fin es obligatoria") LocalDate fin,
        @NotBlank(message = "El estado es obligatorio") String estado
) {}