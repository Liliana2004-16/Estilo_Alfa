package com.uniminuto.dao;

import com.uniminuto.model.Citas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitasRepository extends JpaRepository<Citas, Long> {
    // JpaRepository ya tiene el método findById() por defecto
}
