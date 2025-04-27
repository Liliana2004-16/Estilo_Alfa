package com.uniminuto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniminuto.dao.UsuarioDao;
import com.uniminuto.model.Usuario;

@Service
public class UsuarioService implements UsuarioServiceI {

	// inyeccion por metodo set
	UsuarioDao dao;
	PasswordEncoder passwordEncoder;

	@Autowired
	public void setDao(UsuarioDao dao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		 this.passwordEncoder = passwordEncoder;
	}
	
	@Override
    public Usuario saveUsuario(Usuario usuario) {
        // Verificar que el correo sea único
        if (usuario.getCorreo() != null && dao.searchUsuariocorreo(usuario.getCorreo()) != null) {
            // Si el correo ya está registrado, retornar null
            return null; 
        }
        // Cifrar la contraseña antes de guardar
        String encodedPassword = passwordEncoder.encode(usuario.getContraseña());
        usuario.setContraseña(encodedPassword);  // Establecer la contraseña cifrada

        // Si el correo no existe, guardar el usuario
        return dao.addUsuario(usuario);
    }

	@Override
	public Usuario upUsuario(Usuario usuario) {
	    // Buscar el usuario actual en la base de datos
	    Usuario existente = dao.searchUsuario(usuario.getId());
	    if (existente == null) {
	        throw new IllegalArgumentException("No se encontró el usuario con el ID especificado.");
	    }

	    // Validar que no exista otro usuario con el mismo correo
	    Usuario usuarioConMismoCorreo = dao.searchUsuariocorreo(usuario.getCorreo());
	    if (usuarioConMismoCorreo != null && usuarioConMismoCorreo.getId() != usuario.getId()) {
	        throw new IllegalArgumentException("El correo ya está registrado por otro usuario.");
	    }

	    String encodedPassword = passwordEncoder.encode(usuario.getContraseña());
	    usuario.setContraseña(encodedPassword);

		return dao.updateUsuario(usuario);
	}

	@Override
	public void deleteById(int id) {
		dao.deleteId(id);
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
	@Override
	public Usuario loginU(String correo, String contraseña) {
	    Usuario usuario = dao.searchUsuariocorreo(correo);
	    
	    // Verificar si la contraseña proporcionada coincide con la contraseña cifrada
	    if (usuario != null && passwordEncoder.matches(contraseña, usuario.getContraseña())) {
	        return usuario; // Login exitoso
	    }
	    
	    // Si el usuario no existe o la contraseña es incorrecta
	    return null; 
	}



  
}
