package com.uniminuto.controller;

import com.uniminuto.model.Usuario;
import com.uniminuto.service.UsuarioServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioServiceI usuarioService;

    @PostMapping(value = "/registro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUsuario(@RequestBody Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre vacío");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Correo vacío");
        } else if (!usuario.getCorreo().contains("@") || !usuario.getCorreo().endsWith("@gmail.com")) {
            return ResponseEntity.badRequest().body("Correo inválido");
        }
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Contraseña vacía");
        }
        if (usuario.getRol() == null || !(usuario.getRol().equalsIgnoreCase("Cliente") || usuario.getRol().equalsIgnoreCase("Administrador"))) {
            return ResponseEntity.badRequest().body("Rol inválido");
        }

        Usuario savedUsuario = usuarioService.saveUsuario(usuario);
        if (savedUsuario == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El correo ya existe");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @GetMapping(value="/usuarios", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuario();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Usuarios_Registrados", String.valueOf(usuarios.size()));
        return new ResponseEntity<>(usuarios, headers, HttpStatus.OK);
    }

    @PutMapping(value="/usuario/actualizar", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
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

            Usuario usuarioActualizado = usuarioService.upUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }

    @GetMapping(value="/usuario/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable int id) {
        return ResponseEntity.ok(usuarioService.searchUsuarioById(id));
    }

    @GetMapping(value="/usuario/correo/{correo}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuarioCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.searchUsuarioBycorreo(correo));
    }

    @DeleteMapping(value="/eliminar/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteId(@PathVariable int id) {
        usuarioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contraseña) {
        Usuario usuario = usuarioService.loginU(correo, contraseña);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }
}
