package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Servicio;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.BarberiaService;
import com.example.demo.service.ServicioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/servicios")
public class ServicioController {

    private final ServicioService servicioService;
    private final BarberiaService barberiaService;

    public ServicioController(ServicioService servicioService, BarberiaService barberiaService) {
        this.servicioService = servicioService;
        this.barberiaService = barberiaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", servicioService.findAll());
        return "servicios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("barberias", barberiaService.obtenerActivas());
        return "servicio_form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("servicio") Servicio servicio, BindingResult result, Model model) {
        if (servicio.getBarberia() != null && servicio.getBarberia().getId() == null) {
            result.rejectValue("barberia", "barberia.requerida", "Debes seleccionar una barbería");
        }
        if (result.hasErrors()) {
            model.addAttribute("barberias", barberiaService.obtenerActivas());
            return "servicio_form";
        }
        servicioService.save(servicio);
        return "redirect:/admin/servicios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Servicio servicio = servicioService.findById(id);
        if (servicio == null) {
            throw new RecursoNoEncontradoException("No existe un servicio con id " + id);
        }
        model.addAttribute("servicio", servicio);
        model.addAttribute("barberias", barberiaService.obtenerActivas());
        return "servicio_form";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Integer id) {
        servicioService.desactivar(id);
        return "redirect:/admin/servicios";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Integer id) {
        servicioService.activar(id);
        return "redirect:/admin/servicios";
    }
}
