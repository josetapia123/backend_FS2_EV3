package com.creativamente.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código único de campaña en el frontend, ej: "PR001"
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String cliente;

    @Column(nullable = false, length = 80)
    private String tipo;

    // Almacenado en CLP entero (ej: 12000000) para facilitar sumas y porcentajes en backend/frontend
    @Column(nullable = false)
    private Long presupuesto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate inicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fin;

    @Column(nullable = false, length = 50)
    private String estado;

    // @JsonIgnore evita recursión infinita al serializar JSON con tareas
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tarea> tareas = new ArrayList<>();

    public Proyecto() {}

    public Proyecto(String codigo, String nombre, String cliente, String tipo,
                    Long presupuesto, LocalDate inicio, LocalDate fin, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cliente = cliente;
        this.tipo = tipo;
        this.presupuesto = presupuesto;
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Long presupuesto) { this.presupuesto = presupuesto; }

    public LocalDate getInicio() { return inicio; }
    public void setInicio(LocalDate inicio) { this.inicio = inicio; }

    public LocalDate getFin() { return fin; }
    public void setFin(LocalDate fin) { this.fin = fin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }
}