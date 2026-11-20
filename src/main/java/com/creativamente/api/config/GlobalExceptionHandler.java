package com.creativamente.api.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Maneja excepciones de forma centralizada para devolver respuestas JSON uniformes
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Devuelve HTTP 400 cuando ocurre un error de negocio o entidad no encontrada
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Solicitud inválida");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Devuelve un mapa campo -> mensaje cuando falla la validación (@Valid) del request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erroresCampos = new HashMap<>();

        // Extrae cada error por campo para enviarlo ordenado en la respuesta
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erroresCampos.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Error de validación");
        body.put("fields", erroresCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}