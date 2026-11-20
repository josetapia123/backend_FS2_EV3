package com.creativamente.api.repository;

import com.creativamente.api.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByProyectoCodigo(String codigo);
}