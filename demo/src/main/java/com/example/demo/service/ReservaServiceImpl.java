package com.example.demo.service;

import com.example.demo.entities.Reserva;
import com.example.demo.exception.EspacioNoDisponibleException;
import com.example.demo.repository.PagoRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final PagoRepository pagoRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, PagoRepository pagoRepository) {
        this.reservaRepository = reservaRepository;
        this.pagoRepository = pagoRepository;
    }

    @Override
    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Reserva> findByBarberoId(Long barberoId) {
        return reservaRepository.findByBarberoId(barberoId);
    }

    @Override
    public List<Reserva> findByBarberoIdAndFecha(Long barberoId, LocalDate fecha) {
        return reservaRepository.findByBarberoIdAndFecha(barberoId, fecha);
    }

    @Override
    public List<Reserva> findByBarberiaId(Long barberiaId) {
        return reservaRepository.findByServicioBarberiaId(barberiaId);
    }

    @Override
    public List<Reserva> findByBarberiaIdAndFecha(Long barberiaId, LocalDate fecha) {
        return reservaRepository.findByServicioBarberiaIdAndFecha(barberiaId, fecha);
    }

    @Override
    public Reserva save(Reserva reserva) {
        if (espacioOcupado(reserva)) {
            throw new EspacioNoDisponibleException(
                    "El espacio ya tiene una reserva para esa fecha y hora. Elige otro horario.");
        }
        if (barberoOcupado(reserva)) {
            throw new EspacioNoDisponibleException(
                    "El barbero ya tiene una reserva para esa fecha y hora. Elige otro horario.");
        }
        return reservaRepository.save(reserva);
    }

    @Override
    public void deleteById(Long id) {
        pagoRepository.findByReservaId(id).ifPresent(pagoRepository::delete);
        reservaRepository.deleteById(id);
    }

    private boolean espacioOcupado(Reserva reserva) {
        return reservaRepository
                .findByEspacioIdAndFechaAndHora(reserva.getEspacio().getId(), reserva.getFecha(), reserva.getHora())
                .stream()
                .anyMatch(existente -> !existente.getId().equals(reserva.getId()));
    }

    private boolean barberoOcupado(Reserva reserva) {
        return reservaRepository
                .findByBarberoIdAndFechaAndHora(reserva.getBarbero().getId(), reserva.getFecha(), reserva.getHora())
                .stream()
                .anyMatch(existente -> !existente.getId().equals(reserva.getId()));
    }
}
