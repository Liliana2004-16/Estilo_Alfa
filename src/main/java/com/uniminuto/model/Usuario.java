package com.uniminuto.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="contraseña")
	private String contraseña;

	@Column(name="correo")
	private String correo;

	@Column(name="nombre")
	private String nombre;

	@Column(name="rol")
	private String rol;

	//bi-directional many-to-one association to citas
	@OneToMany(mappedBy="usuarioId")
	private List<Citas> citas;

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContraseña() {
		return this.contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Citas> getCitas() {
		return this.citas;
	}

	public void setCitas(List<Citas> citas) {
		this.citas = citas;
	}

	public Citas addCita(Citas cita) {
		getCitas().add(cita);
		cita.setUsuarioId(this);

		return cita;
	}

	public Citas removeCita(Citas cita) {
		getCitas().remove(cita);
		cita.setUsuarioId(null);

		return cita;
	}

}