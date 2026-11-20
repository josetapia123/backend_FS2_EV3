package com.creativamente.api.controller;

import com.creativamente.api.dto.AuthResponse;
import com.creativamente.api.dto.LoginRequest;
import com.creativamente.api.model.Usuario;
import com.creativamente.api.repository.UsuarioRepository;
import com.creativamente.api.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String usernameNormalizado = request.username().trim().toLowerCase();
        Usuario usuario = usuarioRepository.findByUsername(usernameNormalizado).orElse(null);

        if (usuario == null || !Boolean.TRUE.equals(usuario.getActivo())
                || !passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas. Intente nuevamente.");
        }

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol().name());

        return ResponseEntity.ok(new AuthResponse(
                token,
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getRol().name(),
                usuario.getVista()
        ));
    }
}