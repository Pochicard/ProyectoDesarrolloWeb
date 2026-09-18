package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Reserva;
import com.example.demo.service.EspacioService;
import com.example.demo.service.ReservaService;
import com.example.demo.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EspacioService espacioService;
    @GetMapping
    public String mostrarReservas(@RequestParam(required = false) Long usuarioId, Model model) {
        if (usuarioId != null) {
            model.addAttribute("reservas", reservaService.findByUsuarioId(usuarioId));
        } else {
            model.addAttribute("reservas", reservaService.findAll());
        }
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("usuarioId", usuarioId);
        return "mostrar_reservas";
    }
    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("usuarios", usuarioService.buscarActivos());
        model.addAttribute("espacios", espacioService.obtenerActivos());
        return "reserva_form";
    }
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.findById(id));
        return "reserva_detalle";
    }
    @PostMapping("/guardar")
    public String guardarReserva(@Valid @ModelAttribute("reserva") Reserva reserva, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.buscarActivos());
            model.addAttribute("espacios", espacioService.obtenerActivos());
            return "reserva_form";
        }
        reservaService.save(reserva);
        return "redirect:/reservas";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.findById(id));
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("espacios", espacioService.obtenerTodos()); // Método exacto de EspacioService
        return "reserva_form";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservaService.deleteById(id);
        return "redirect:/reservas";
    }
}