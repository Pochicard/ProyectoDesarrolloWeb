package com.example.demo.service;

import com.example.demo.entities.Servicio;
import java.util.Collection;

public interface ServicioService {
    Servicio findById(Integer id);
    Collection<Servicio> findAll();
    Servicio save(Servicio servicio);
}