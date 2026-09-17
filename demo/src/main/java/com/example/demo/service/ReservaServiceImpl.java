package com.example.demo.service;

import com.example.demo.entities.Reserva;
import com.example.demo.repository.PagoRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public Collection<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public void deleteById(Long id) {
        // El pago tiene una FK obligatoria hacia la reserva: hay que borrarlo antes
        // o la eliminación de la reserva falla por violación de integridad referencial.
        pagoRepository.findByReservaId(id).ifPresent(pagoRepository::delete);
        reservaRepository.deleteById(id);
    }
}