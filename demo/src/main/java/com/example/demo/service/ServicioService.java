package com.example.demo.service;

import com.example.demo.entities.Servicio;
import java.util.List;

public interface ServicioService {
    Servicio findById(Integer id);
    List<Servicio> findAll();
    List<Servicio> findByBarberiaId(Long barberiaId);
    List<Servicio> findTodosPorBarberiaId(Long barberiaId);
    Servicio save(Servicio servicio);
    void desactivar(Integer id);
    void activar(Integer id);
}
