package com.uniminuto.service;

import java.util.List;



import com.uniminuto.model.Usuario;


public interface UsuarioServiceI {
	
	Usuario saveUsuario (Usuario usuario);
	Usuario upUsuario(Usuario usuario);
	void deleteId(int id);
	Usuario searchUsuarioById(int id); 
	Usuario searchUsuarioBycorreo(String correo);
	List<Usuario> getAllUsuario();
	

}
