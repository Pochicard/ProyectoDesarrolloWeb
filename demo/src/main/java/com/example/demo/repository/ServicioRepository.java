package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Servicio;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    List<Servicio> findByBarberiaIdAndActivoTrue(Long barberiaId);

    List<Servicio> findByBarberiaId(Long barberiaId);
}
