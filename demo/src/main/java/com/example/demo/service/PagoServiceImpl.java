package com.example.demo.service;

import com.example.demo.entities.Pago;
import com.example.demo.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public Pago findById(Integer id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }
}