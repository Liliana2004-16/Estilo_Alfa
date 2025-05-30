package com.uniminuto.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uniminuto.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
public class UsuarioDao implements UsuarioDaoI{
	
	//inyeccion fiel a traves interface
	@Autowired
	UsuarioJpa Jpa;

	@Override
	public Usuario addUsuario(Usuario usuario) {
		return Jpa.save(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return Jpa.save(usuario);
	}

	@Override
	@Transactional
	 public void deleteId(int id) {
	    Usuario usuario = Jpa.findById(id)
	            .orElseThrow();
	        
	    	Jpa.delete(usuario);
	}

	@Override
	public Usuario searchUsuario(int id) {
		return Jpa.findById(id).orElse(null);
	}

	@Override
	public Usuario searchUsuariocorreo(String correo) {
		return Jpa.findByCorreo(correo);
	}

	@Override
	public List<Usuario> getUsuario() {
		return Jpa.findAll();
	}

}
