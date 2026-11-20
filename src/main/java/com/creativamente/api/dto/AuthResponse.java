package com.creativamente.api.dto;

// Devuelve el token JWT junto con los datos de sesión y vista que necesita el frontend
public record AuthResponse(
        String token,
        String username,
        String nombre,
        String rol,
        String vista
) {}