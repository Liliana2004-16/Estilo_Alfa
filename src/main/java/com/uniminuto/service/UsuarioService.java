package com.uniminuto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniminuto.dao.UsuarioDao;
import com.uniminuto.model.Usuario;

@Service
public class UsuarioService implements UsuarioServiceI {

    private UsuarioDao dao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setDao(UsuarioDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getCorreo() != null && dao.searchUsuariocorreo(usuario.getCorreo()) != null) {
            return null; // El correo ya existe
        }

        // Cifrar la contraseña antes de guardar
        String encodedPassword = passwordEncoder.encode(usuario.getContraseña());
        usuario.setContraseña(encodedPassword);

        return dao.addUsuario(usuario);
    }

    @Override
    public Usuario upUsuario(Usuario usuario) {
        Usuario existente = dao.searchUsuario(usuario.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró el usuario con el ID especificado.");
        }

        // Verificar si el nuevo correo ya está en uso por otro usuario
        Usuario usuarioConMismoCorreo = dao.searchUsuariocorreo(usuario.getCorreo());
        if (usuarioConMismoCorreo != null && usuarioConMismoCorreo.getId() != usuario.getId()) {
            throw new IllegalArgumentException("El correo ya está registrado por otro usuario.");
        }

        // Cifrar nueva contraseña
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
        if (usuario != null && passwordEncoder.matches(contraseña, usuario.getContraseña())) {
            return usuario;
        }
        return null; // Usuario no encontrado o contraseña incorrecta
    }
}
