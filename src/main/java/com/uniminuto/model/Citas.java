package com.uniminuto.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the Citas database table.
 * 
 */
@Entity
@Table(name="citas")
@NamedQuery(name="citas.findAll", query="SELECT c FROM citas c")
public class Citas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="estado")
	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha")
	private Date fecha;

	@Column(name="hora")
	private Time hora;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name = "servicio_id")
	private Servicio servicioId;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuarioId;

	public Citas() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Servicio getServicioId() {
		return this.servicioId;
	}

	public void setServicioId(Servicio servicioId) {
		this.servicioId = servicioId;
	}

	public Usuario getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
	}

}


