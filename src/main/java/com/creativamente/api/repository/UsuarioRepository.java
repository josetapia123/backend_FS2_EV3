package com.creativamente.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creativamente.api.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}