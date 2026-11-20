package com.creativamente.api.service;

import com.creativamente.api.dto.TareaRequest;
import com.creativamente.api.model.Proyecto;
import com.creativamente.api.model.Tarea;
import com.creativamente.api.repository.ProyectoRepository;
import com.creativamente.api.repository.TareaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public TareaService(TareaRepository tareaRepository, ProyectoRepository proyectoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional(readOnly = true)
    public List<Tarea> listarTodas() {
        return tareaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarea obtenerPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con id: " + id));
    }

    public Tarea crear(TareaRequest req) {
        Proyecto proyecto = buscarProyectoPorCodigo(req.proyectoCodigo());
        Tarea tarea = new Tarea(
                req.especialista().trim(),
                req.tarea().trim(),
                proyecto,
                req.estado().trim(),
                req.prioridad().trim(),
                req.horas()
        );
        return tareaRepository.save(tarea);
    }

    public Tarea actualizar(Long id, TareaRequest req) {
        Tarea existente = obtenerPorId(id);
        Proyecto proyecto = buscarProyectoPorCodigo(req.proyectoCodigo());

        existente.setEspecialista(req.especialista().trim());
        existente.setTarea(req.tarea().trim());
        existente.setProyecto(proyecto);
        existente.setEstado(req.estado().trim());
        existente.setPrioridad(req.prioridad().trim());
        existente.setHoras(req.horas());

        return tareaRepository.save(existente);
    }

    // Permite cambiar rápidamente el estado de una tarea (ej. desde el botón "Iniciar" en VistaEspecialista)
    public Tarea actualizarEstado(Long id, String nuevoEstado) {
        Tarea existente = obtenerPorId(id);
        existente.setEstado(nuevoEstado.trim());
        return tareaRepository.save(existente);
    }

    public void eliminar(Long id) {
        Tarea existente = obtenerPorId(id);
        tareaRepository.delete(existente);
    }

    private Proyecto buscarProyectoPorCodigo(String codigo) {
        String codigoNormalizado = codigo.trim().toUpperCase();
        return proyectoRepository.findByCodigo(codigoNormalizado)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con código: " + codigoNormalizado));
    }
}