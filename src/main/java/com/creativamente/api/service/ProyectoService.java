package com.creativamente.api.service;

import com.creativamente.api.dto.ProyectoRequest;
import com.creativamente.api.entity.Proyecto;
import com.creativamente.api.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional(readOnly = true)
    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Proyecto obtenerPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con id: " + id));
    }

    public Proyecto crear(ProyectoRequest req) {
        String codigoNormalizado = req.codigo().trim().toUpperCase();
        if (proyectoRepository.existsByCodigo(codigoNormalizado)) {
            throw new IllegalArgumentException("Ya existe un proyecto con el código: " + codigoNormalizado);
        }
        validarRangoFechas(req);

        Proyecto proyecto = new Proyecto(
                codigoNormalizado,
                req.nombre().trim(),
                req.cliente().trim(),
                req.tipo().trim(),
                req.presupuesto(),
                req.inicio(),
                req.fin(),
                req.estado().trim()
        );
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizar(Long id, ProyectoRequest req) {
        Proyecto existente = obtenerPorId(id);
        String codigoNormalizado = req.codigo().trim().toUpperCase();

        // Verifica que el nuevo código no pertenezca a otro proyecto distinto
        proyectoRepository.findByCodigo(codigoNormalizado)
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new IllegalArgumentException("El código ya está en uso por otro proyecto: " + codigoNormalizado);
                });

        validarRangoFechas(req);

        existente.setCodigo(codigoNormalizado);
        existente.setNombre(req.nombre().trim());
        existente.setCliente(req.cliente().trim());
        existente.setTipo(req.tipo().trim());
        existente.setPresupuesto(req.presupuesto());
        existente.setInicio(req.inicio());
        existente.setFin(req.fin());
        existente.setEstado(req.estado().trim());

        return proyectoRepository.save(existente);
    }

    public void eliminar(Long id) {
        Proyecto existente = obtenerPorId(id);
        proyectoRepository.delete(existente);
    }

    private void validarRangoFechas(ProyectoRequest req) {
        if (req.fin().isBefore(req.inicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }
}