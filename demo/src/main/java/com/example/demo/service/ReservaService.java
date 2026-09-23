package com.example.demo.service;

import com.example.demo.entities.Reserva;
import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    Reserva findById(Long id);
    List<Reserva> findAll();
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByBarberoId(Long barberoId);
    List<Reserva> findByBarberoIdAndFecha(Long barberoId, LocalDate fecha);
    List<Reserva> findByBarberiaId(Long barberiaId);
    List<Reserva> findByBarberiaIdAndFecha(Long barberiaId, LocalDate fecha);
    Reserva save(Reserva reserva);
    void deleteById(Long id);
}
