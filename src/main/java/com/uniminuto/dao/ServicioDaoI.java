package com.uniminuto.dao;

import java.util.List;

import com.uniminuto.model.Servicio;

public interface ServicioDaoI {
	Servicio addServicio (Servicio servicio);//Agregar Servicio
	Servicio updateUsuario (Servicio servicio);//Agregar Actualizar Servicio
	void deleteld(int id);//Eliminar Servicio
	Servicio searchServicio(int id);
	List<Servicio> getServicio();

}
