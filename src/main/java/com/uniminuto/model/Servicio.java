package com.uniminuto.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;




/**
 * The persistent class for the servicios database table.
 * 
 */
@Entity
@Table(name="servicios")
@NamedQuery(name="Servicio.findAll", query="SELECT s FROM Servicio s")
public class Servicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="duracion_minutos")
	private int duracionMinutos;

	private String nombre;

	private BigDecimal precio;

	//bi-directional many-to-one association to citas
	@OneToMany(mappedBy="servicio")
	private List<Citas> citas;

	public Servicio() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuracionMinutos() {
		return this.duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public List<Citas> getCitas() {
		return this.citas;
	}

	public void setCitas(List<Citas> citas) {
		this.citas = citas;
	}

	public Citas addCita(Citas cita) {
		getCitas().add(cita);
		cita.setServicioId(this);

		return cita;
	}

	public Citas removeCita(Citas cita) {
		getCitas().remove(cita);
		cita.setServicioId(null);

		return cita;
	}

}