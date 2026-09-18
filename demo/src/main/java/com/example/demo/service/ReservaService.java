package com.example.demo.service;

import com.example.demo.entities.Reserva;
import java.util.Collection;

public interface ReservaService {
    Reserva findById(Long id);
    Collection<Reserva> findAll();
    Collection<Reserva> findByUsuarioId(Long usuarioId);
    Reserva save(Reserva reserva);
    void deleteById(Long id);
}