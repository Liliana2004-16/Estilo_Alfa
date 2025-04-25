package com.uniminuto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniminuto.dao.UsuarioDao;
import com.uniminuto.model.Usuario;

@Service
public class UsuarioService implements UsuarioServiceI {

	// inyeccion por metodo set
	UsuarioDao dao;

	@Autowired
	public void setDao(UsuarioDao dao) {
		this.dao = dao;
	}
	
	@Override
	public Usuario saveUsuario(Usuario usuario) {
	    if (usuario.getCorreo() != null && dao.searchUsuariocorreo(usuario.getCorreo()) == null) {
	        return dao.addUsuario(usuario);
	    } else {
	        throw new IllegalArgumentException("El correo ya está registrado.");
	    }
		/*verificar que no exista el correo
	    if (usuario.getCorreo() != null && dao.searchUsuariocorreo(usuario.getCorreo()) == null) {
	        return dao.addUsuario(usuario);
	    } else {
	        return null; 
	    }*/
	}


	@Override
	public Usuario upUsuario(Usuario usuario) {
		// busqueda por is, si el id exite hace la actualizacion
		if (usuario.getId() <= 0) {
	        throw new IllegalArgumentException("ID de usuario inválido para actualización.");
	    }

	    // Buscar el usuario actual en la base de datos
	    Usuario existente = dao.searchUsuario(usuario.getId());
	    if (existente == null) {
	        throw new IllegalArgumentException("No se encontró el usuario con el ID especificado.");
	    }

	    // Validar que el correo no esté en uso por otro usuario
	    Usuario usuarioConMismoCorreo = dao.searchUsuariocorreo(usuario.getCorreo());
	    if (usuarioConMismoCorreo != null && usuarioConMismoCorreo.getId() != usuario.getId()) {
	        throw new IllegalArgumentException("El correo ya está registrado por otro usuario.");
	    }

	    // Validar campos obligatorios
	    if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
	        throw new IllegalArgumentException("El correo no puede estar vacío.");
	    }

	    if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
	        throw new IllegalArgumentException("El nombre no puede estar vacío.");
	    }

	    if (usuario.getContraseña() == null || usuario.getContraseña().isBlank()) {
	        throw new IllegalArgumentException("La contraseña es obligatoria.");
	    }

		return dao.updateUsuario(usuario);
	}

	@Override
	public void deleteId(int id) {
		// TODO Auto-generated method stub	
	}

	@Override
	public Usuario searchUsuarioById(int id) {
		return dao.searchUsuario(id);
	}

	@Override
	public Usuario searchUsuarioBycorreo(String correo) {
		return dao.searchUsuariocorreo(correo);
	}

	@Override
	public List<Usuario> getAllUsuario() {
		return dao.getUsuario();
	}
	
  
}
