package com.example.demo.service;

import com.example.demo.entities.Espacio;
import java.util.List;

public interface EspacioService {
    List<Espacio> obtenerTodos();
    List<Espacio> obtenerActivos();
    List<Espacio> obtenerPorCapacidadMinima(Integer capacidadMinima);
    Espacio guardar(Espacio espacio);
    Espacio obtenerPorId(Long id);
    void desactivar(Long id);
    void activar(Long id);
}