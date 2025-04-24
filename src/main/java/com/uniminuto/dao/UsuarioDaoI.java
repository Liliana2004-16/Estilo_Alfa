package com.uniminuto.dao;

import java.util.List;

import com.uniminuto.model.Usuario;

public interface UsuarioDaoI {
	
	Usuario addUsuario (Usuario usuario);//Agregar usuario
	
	Usuario updateUsuario(Usuario usuario);//Agregar Actualizar
	
	void deleteId(int id);//Eliminar
	
	Usuario searchUsuario(int id);// busqueda por id 
	Usuario searchUsuariocorreo(String email);//Busqueda por correo
	List<Usuario> getUsuario();// lista de usuarios
	
	

}
