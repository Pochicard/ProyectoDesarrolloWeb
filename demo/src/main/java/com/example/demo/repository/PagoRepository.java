package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    // Permite consultar y guardar pagos sin escribir SQL manual.

    Optional<Pago> findByReservaId(Long reservaId);

    List<Pago> findByReservaServicioBarberiaId(Long barberiaId);
}
