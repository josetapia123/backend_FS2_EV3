package com.creativamente.api.repository;

import com.creativamente.api.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}