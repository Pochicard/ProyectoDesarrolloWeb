package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Barberia;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.BarberiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/barberias")
public class BarberiaController {

    private final BarberiaService barberiaService;

    public BarberiaController(BarberiaService barberiaService) {
        this.barberiaService = barberiaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("barberias", barberiaService.obtenerTodas());
        return "barberias";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("barberia", new Barberia());
        return "barberia_form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("barberia") Barberia barberia, BindingResult result) {
        if (result.hasErrors()) {
            return "barberia_form";
        }
        barberiaService.guardar(barberia);
        return "redirect:/admin/barberias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Barberia barberia = barberiaService.obtenerPorId(id);
        if (barberia == null) {
            throw new RecursoNoEncontradoException("No existe una barbería con id " + id);
        }
        model.addAttribute("barberia", barberia);
        return "barberia_form";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        barberiaService.desactivar(id);
        return "redirect:/admin/barberias";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        barberiaService.activar(id);
        return "redirect:/admin/barberias";
    }
}
