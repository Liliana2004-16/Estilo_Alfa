package com.uniminuto.service;

import com.uniminuto.dao.CitasRepository;
import com.uniminuto.model.Citas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitasService {

    @Autowired
    private CitasRepository citasRepository;

    public List<Citas> obtenerTodas() {
        return citasRepository.findAll();
    }

    public Optional<Citas> obtenerPorId(Long id) {
        return citasRepository.findById(id);
    }

    public Citas guardar(Citas cita) {
        return citasRepository.save(cita);
    }

    public void eliminar(Long id) {
        citasRepository.deleteById(id);
    }
}
