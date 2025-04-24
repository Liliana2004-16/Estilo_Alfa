package com.uniminuto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniminuto.dao.UsuarioDao;
import com.uniminuto.model.Usuario;

@Service
public class UsuarioService implements UsuarioServiceI {

	// inyeccion por metodo set
	UsuarioDao Dao;

	@Autowired
	public void setDao(UsuarioDao dao) {
		Dao = dao;
	}

	@Override
	public Usuario saveUsuario(Usuario usuario) {
		// verificar que no exista el correo
	    if (usuario.getCorreo() != null && Dao.searchUsuariocorreo(usuario.getCorreo()) == null) {
	        return Dao.addUsuario(usuario);
	    } else {
	        return null; 
	    }
	}

	@Override
	public Usuario upUsuario(Usuario usuario) {
		// busqueda por id, validacion que el correo no exista 
		return Dao.updateUsuario(usuario);
	}

	@Override
	public void deleteId(int id) {
		// TODO Auto-generated method stub	
	}

	@Override
	public Usuario searchUsuarioById(int id) {
		return Dao.searchUsuario(id);
	}

	@Override
	public Usuario searchUsuarioBycorreo(String correo) {
		return Dao.searchUsuariocorreo(correo);
	}

	@Override
	public List<Usuario> getAllUsuario() {
		return Dao.getUsuario();
	}
	
  
}
