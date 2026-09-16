package com.example.demo.repository;

import com.example.demo.entities.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    @Query("SELECT e FROM Espacio e WHERE e.capacidad >= :capacidadMinima")
    List<Espacio> buscarPorCapacidadMinima(@Param("capacidadMinima") Integer capacidadMinima);
}