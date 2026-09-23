package com.example.demo.controller;

import com.example.demo.entities.Espacio;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.BarberiaService;
import com.example.demo.service.EspacioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/espacios")
public class EspacioController {

    private final EspacioService espacioService;
    private final BarberiaService barberiaService;

    public EspacioController(EspacioService espacioService, BarberiaService barberiaService) {
        this.espacioService = espacioService;
        this.barberiaService = barberiaService;
    }

    @GetMapping
    public String listarEspacios(@RequestParam(required = false) Integer capacidadMinima, Model model) {
        if (capacidadMinima != null) {
            model.addAttribute("espacios", espacioService.obtenerPorCapacidadMinima(capacidadMinima));
        } else {
            model.addAttribute("espacios", espacioService.obtenerTodos());
        }
        model.addAttribute("capacidadMinima", capacidadMinima);
        return "espacios";
    }
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("espacio", new Espacio());
        model.addAttribute("barberias", barberiaService.obtenerActivas());
        return "espacio_form";
    }

    @PostMapping("/guardar")
    public String guardarEspacio(@Valid @ModelAttribute("espacio") Espacio espacio, BindingResult result, Model model) {
        if (espacio.getBarberia() != null && espacio.getBarberia().getId() == null) {
            result.rejectValue("barberia", "barberia.requerida", "Debes seleccionar una barbería");
        }
        if (result.hasErrors()) {
            model.addAttribute("barberias", barberiaService.obtenerActivas());
            return "espacio_form";
        }
        espacioService.guardar(espacio);
        return "redirect:/admin/espacios";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Espacio espacio = espacioService.obtenerPorId(id);
        if (espacio == null) {
            throw new RecursoNoEncontradoException("No existe un espacio con id " + id);
        }
        model.addAttribute("espacio", espacio);
        model.addAttribute("barberias", barberiaService.obtenerActivas());
        return "espacio_form";
    }
    @GetMapping("/desactivar/{id}")
    public String desactivarEspacio(@PathVariable Long id) {
        espacioService.desactivar(id);
        return "redirect:/admin/espacios";
    }

    @GetMapping("/activar/{id}")
    public String activarEspacio(@PathVariable Long id) {
        espacioService.activar(id);
        return "redirect:/admin/espacios";
    }
}