package com.uniminuto.controller;


import com.uniminuto.model.Usuario;
import com.uniminuto.service.UsuarioServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UsuarioController {

    @Autowired
    UsuarioServiceI usuarioService;
    
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.saveUsuario(usuario);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // Puedes devolver un mensaje de error
        }
    }


    /*POST - Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }*/

    // PUT - Actualizar usuario
    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.upUsuario(usuario);
        return ResponseEntity.ok(actualizado);
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = usuarioService.searchUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    // GET - Buscar por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.searchUsuarioBycorreo(correo);
        return ResponseEntity.ok(usuario);
    }

    // DELETE - Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
        usuarioService.deleteId(id);
        return ResponseEntity.noContent().build();
    }

    // GET - Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.getAllUsuario();
        return ResponseEntity.ok(usuarios);
    }

    // POST - Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario credenciales) {
        if (credenciales.getCorreo() == null || credenciales.getContraseña() == null) {
            return ResponseEntity.badRequest().body("Correo y contraseña son obligatorios.");
        }

        Usuario usuario = usuarioService.searchUsuarioBycorreo(credenciales.getCorreo());

        if (usuario != null && usuario.getContraseña().equals(credenciales.getContraseña())) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos.");
        }
    }
}
