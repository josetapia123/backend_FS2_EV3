package com.creativamente.api.dto;

import jakarta.validation.constraints.NotBlank;

// Recibe las credenciales enviadas desde el formulario de login del frontend
public record LoginRequest(
        @NotBlank(message = "El usuario es obligatorio") String username,
        @NotBlank(message = "La contraseña es obligatoria") String password
) {}