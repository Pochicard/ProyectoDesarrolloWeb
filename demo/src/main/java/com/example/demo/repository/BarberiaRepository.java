package com.example.demo.repository;

import com.example.demo.entities.Barberia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberiaRepository extends JpaRepository<Barberia, Long> {

    List<Barberia> findByActivoTrue();
}
