package com.uniminuto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public Citas() {}

    public Citas(LocalDateTime fechaHora, String descripcion, Usuario usuario) {
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Citas{id=" + id + ", fechaHora=" + fechaHora + ", descripcion='" + descripcion + "', usuario=" + usuario + "}";
    }
}
