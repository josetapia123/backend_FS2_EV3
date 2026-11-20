package com.creativamente.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String especialista;

    // Mapeado a "tarea" en JSON para mantener el mismo nombre de campo que usa el frontend
    @Column(name = "descripcion_tarea", nullable = false, length = 255)
    private String tarea;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @Column(nullable = false, length = 40)
    private String estado;

    @Column(nullable = false, length = 20)
    private String prioridad;

    @Column(nullable = false)
    private Integer horas;

    public Tarea() {}

    public Tarea(String especialista, String tarea, Proyecto proyecto, String estado, String prioridad, Integer horas) {
        this.especialista = especialista;
        this.tarea = tarea;
        this.proyecto = proyecto;
        this.estado = estado;
        this.prioridad = prioridad;
        this.horas = horas;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEspecialista() { return especialista; }
    public void setEspecialista(String especialista) { this.especialista = especialista; }

    public String getTarea() { return tarea; }
    public void setTarea(String tarea) { this.tarea = tarea; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public Integer getHoras() { return horas; }
    public void setHoras(Integer horas) { this.horas = horas; }
}