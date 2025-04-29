package com.uniminuto.controller;

import com.uniminuto.model.Citas;
import com.uniminuto.model.Usuario;
import com.uniminuto.service.CitasService;
import com.uniminuto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitasController {

    @Autowired
    private CitasService citasService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Citas> listarTodas() {
        return citasService.obtenerTodas();
    }

    @PostMapping
    public Citas crear(@RequestBody Citas cita) {
        // Verificamos si el usuario existe
        Optional<Usuario> usuarioOptional = Optional.ofNullable(usuarioService.searchUsuarioById(cita.getUsuario().getId()));
        if (!usuarioOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + cita.getUsuario().getId());
        }

        // Asociamos el usuario encontrado a la cita
        cita.setUsuario(usuarioOptional.get());

        // Guardamos la cita con el usuario asociado
        return citasService.guardar(cita);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        citasService.eliminar(id);
    }

    @GetMapping("/{id}")
    public Citas obtenerPorId(@PathVariable Long id) {
        Optional<Citas> cita = citasService.obtenerPorId(id);
        return cita.orElse(null);
    }
}
