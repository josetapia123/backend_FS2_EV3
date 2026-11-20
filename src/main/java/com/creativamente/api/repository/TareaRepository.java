package com.creativamente.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creativamente.api.model.Tarea;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByProyectoCodigo(String codigo);
}