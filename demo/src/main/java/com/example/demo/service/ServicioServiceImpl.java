package com.example.demo.service;

import com.example.demo.entities.Servicio;
import com.example.demo.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public Servicio findById(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public List<Servicio> findByBarberiaId(Long barberiaId) {
        return servicioRepository.findByBarberiaIdAndActivoTrue(barberiaId);
    }

    @Override
    public List<Servicio> findTodosPorBarberiaId(Long barberiaId) {
        return servicioRepository.findByBarberiaId(barberiaId);
    }

    @Override
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public void desactivar(Integer id) {
        Servicio servicio = findById(id);
        if (servicio != null) {
            servicio.setActivo(false);
            servicioRepository.save(servicio);
        }
    }

    @Override
    public void activar(Integer id) {
        Servicio servicio = findById(id);
        if (servicio != null) {
            servicio.setActivo(true);
            servicioRepository.save(servicio);
        }
    }
}
