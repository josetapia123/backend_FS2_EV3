package com.creativamente.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coincide con las claves de usuariosData ("admin", "director", "especialista", "cliente")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 120)
    private String nombre;

    // Siempre almacenamos el hash generado con BCryptPasswordEncoder, nunca texto plano
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RolNombre rol;

    // Mantiene compatibilidad con el nombre de vista que usa el frontend ("vista-admin", etc.)
    @Column(nullable = false, length = 40)
    private String vista;

    @Column(nullable = false)
    private Boolean activo = true;

    public Usuario() {}

    public Usuario(String username, String nombre, String passwordHash, RolNombre rol, String vista, Boolean activo) {
        this.username = username;
        this.nombre = nombre;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.vista = vista;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public RolNombre getRol() { return rol; }
    public void setRol(RolNombre rol) { this.rol = rol; }

    public String getVista() { return vista; }
    public void setVista(String vista) { this.vista = vista; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}