package com.creativamente.api.controller;

import com.creativamente.api.dto.TareaRequest;
import com.creativamente.api.model.Tarea;
import com.creativamente.api.service.TareaService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listar() {
        return ResponseEntity.ok(tareaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@Valid @RequestBody TareaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TareaRequest request
    ) {
        return ResponseEntity.ok(tareaService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Tarea> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String nuevoEstado = body.get("estado");
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El campo 'estado' es obligatorio");
        }
        return ResponseEntity.ok(tareaService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}