package com.example.demo.repository;

import com.example.demo.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

    List<Reserva> findByBarberoId(Long barberoId);

    List<Reserva> findByBarberoIdAndFecha(Long barberoId, LocalDate fecha);

    List<Reserva> findByServicioBarberiaId(Long barberiaId);

    List<Reserva> findByServicioBarberiaIdAndFecha(Long barberiaId, LocalDate fecha);

    List<Reserva> findByEspacioIdAndFechaAndHora(Long espacioId, LocalDate fecha, LocalTime hora);

    List<Reserva> findByBarberoIdAndFechaAndHora(Long barberoId, LocalDate fecha, LocalTime hora);
}
