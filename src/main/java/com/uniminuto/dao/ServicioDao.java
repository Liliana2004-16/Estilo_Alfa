package com.uniminuto.dao;

import com.uniminuto.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioDao extends JpaRepository<Servicio, Long> {
}
