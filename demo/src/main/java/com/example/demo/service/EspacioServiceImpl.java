package com.example.demo.service;

import com.example.demo.entities.Espacio;
import com.example.demo.repository.EspacioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacioServiceImpl implements EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioServiceImpl(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    @Override
    public List<Espacio> obtenerTodos() {
        return espacioRepository.findAll();
    }

    @Override
    public List<Espacio> obtenerActivos() {
        return espacioRepository.findByActivoTrue();
    }

    @Override
    public List<Espacio> obtenerPorBarberia(Long barberiaId) {
        return espacioRepository.findByBarberiaIdAndActivoTrue(barberiaId);
    }

    @Override
    public List<Espacio> obtenerTodosPorBarberia(Long barberiaId) {
        return espacioRepository.findByBarberiaId(barberiaId);
    }

    @Override
    public List<Espacio> obtenerPorCapacidadMinima(Integer capacidadMinima) {
        return espacioRepository.buscarPorCapacidadMinima(capacidadMinima);
    }

    @Override
    public Espacio guardar(Espacio espacio) {
        return espacioRepository.save(espacio);
    }

    @Override
    public Espacio obtenerPorId(Long id) {
        return espacioRepository.findById(id).orElse(null);
    }

    @Override
    public void desactivar(Long id) {
        Espacio espacio = obtenerPorId(id);
        if (espacio != null) {
            espacio.setActivo(false);
            espacioRepository.save(espacio);
        }
    }

    @Override
    public void activar(Long id) {
        Espacio espacio = obtenerPorId(id);
        if (espacio != null) {
            espacio.setActivo(true);
            espacioRepository.save(espacio);
        }
    }
}