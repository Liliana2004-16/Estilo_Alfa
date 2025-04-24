package com.uniminuto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.model.Usuario;

@Repository
public interface UsuarioJpa extends JpaRepository<Usuario, Integer> {
	Usuario findByCorreo(String correo);
	
}
