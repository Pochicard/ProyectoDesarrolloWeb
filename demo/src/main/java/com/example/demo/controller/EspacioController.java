package com.example.demo.controller;

import com.example.demo.entities.Espacio;
import com.example.demo.service.EspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/espacios")
public class EspacioController {

    @Autowired
    private EspacioService espacioService;

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
        return "espacio_form";
    }

    @PostMapping("/guardar")
    public String guardarEspacio(@Valid @ModelAttribute("espacio") Espacio espacio, BindingResult result) {
        if (result.hasErrors()) {
            return "espacio_form";
        }
        espacioService.guardar(espacio);
        return "redirect:/admin/espacios";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("espacio", espacioService.obtenerPorId(id));
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