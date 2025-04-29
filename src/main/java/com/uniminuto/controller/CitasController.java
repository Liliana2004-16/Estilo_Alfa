package com.uniminuto.controller;

import com.uniminuto.model.Citas;
import com.uniminuto.service.CitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitasController {

    @Autowired
    private CitasService citasService;

    @GetMapping
    public List<Citas> listarTodas() {
        return citasService.obtenerTodas();
    }

    @PostMapping
    public Citas crear(@RequestBody Citas cita) {
        return citasService.guardar(cita);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        citasService.eliminar(id);
    }

    // Nuevo endpoint para obtener una cita por ID
    @GetMapping("/{id}")
    public Citas obtenerPorId(@PathVariable Long id) {
        Optional<Citas> cita = citasService.obtenerPorId(id);
        return cita.orElse(null); // Devuelve la cita si existe, de lo contrario null
    }
}
