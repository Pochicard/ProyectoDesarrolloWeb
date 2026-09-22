package com.example.demo.service;

import com.example.demo.entities.Pago;
import java.util.Collection;

public interface PagoService {
    Pago findById(Integer id);
    Pago findByReservaId(Long reservaId);
    Collection<Pago> findAll();
    Pago save(Pago pago);
    void deleteById(Integer id);
}
