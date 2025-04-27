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
    
    @PostMapping(value = "registro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
    	  // Validación de nombre
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            usuario.setNombre("Error: Nombre vacío");
        }

        // Validación de correo 
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            usuario.setCorreo("Error: Correo vacío");
        } else if (!usuario.getCorreo().contains("@") || !usuario.getCorreo().endsWith("@gmail.com")) {
            usuario.setCorreo("Error: Correo inválido.");
        }

        // Validación de contraseña
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            usuario.setContraseña("Error: Contraseña vacía.");
        }

        // Validación de rol
        if (usuario.getRol() == null || !(usuario.getRol().equalsIgnoreCase("Cliente") || usuario.getRol().equalsIgnoreCase("Administrador"))) {
            usuario.setRol("Error: Rol inválido.");
        }

        // Si algún campo tiene error, retornamos un error 400 con el JSON correspondiente
        if (usuario.getNombre().startsWith("Error") || usuario.getCorreo().startsWith("Error") || usuario.getContraseña().startsWith("Error") || usuario.getRol().startsWith("Error")) {
            return new ResponseEntity<>(usuario, HttpStatus.BAD_REQUEST);
        }

        // Si pasa todas las validaciones, intentar guardar el usuario
        Usuario savedUsuario = usuarioService.saveUsuario(usuario);

        // Si no se pudo guardar, devolver error
        if (savedUsuario == null) {
            usuario.setCorreo("Error: El correo ya existe ");
            return new ResponseEntity<>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
    }
     
    
    @GetMapping(value="usuarios", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
    	List<Usuario> usuario = usuarioService.getAllUsuario();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Usuarios_Registrados",String.valueOf(usuario.size()));
    	return new ResponseEntity<List<Usuario>>(usuario,headers,HttpStatus.OK);
    }

    /*@PutMapping(value="usuario/actualizar",produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario){
    	return new ResponseEntity<Usuario>(usuarioService.upUsuario(usuario),HttpStatus.ALREADY_REPORTED);
    }*/
    
    @PutMapping(value = "usuario/actualizar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario) {
        try {
       
            if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
                return ResponseEntity.badRequest().body("El correo no puede estar vacío.");
            }
            if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
                return ResponseEntity.badRequest().body("El nombre no puede estar vacío.");
            }
            if (usuario.getContraseña() == null || usuario.getContraseña().isBlank()) {
                return ResponseEntity.badRequest().body("La contraseña es obligatoria.");
            }

            // Si pasa las validaciones de datos, llamar al servicio
            Usuario usuarioActualizado = usuarioService.upUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }


    @GetMapping(value="usuario/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable("id") int id) {
        return new ResponseEntity<Usuario>(usuarioService.searchUsuarioById(id),HttpStatus.OK);
    }
    
    @GetMapping(value="usuario/correo/{correo}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuarioCorreo(@PathVariable("correo") String correo) {
        return new ResponseEntity<Usuario>(usuarioService.searchUsuarioBycorreo(correo),HttpStatus.OK);
    }

    
    @DeleteMapping(value = "eliminar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteId(@PathVariable("id") int id) {
        usuarioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
    

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contraseña) {
        Usuario usuario = usuarioService.loginU(correo, contraseña);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }

}
