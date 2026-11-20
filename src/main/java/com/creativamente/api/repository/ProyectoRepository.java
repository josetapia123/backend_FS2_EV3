package com.creativamente.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creativamente.api.model.Proyecto;

import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}