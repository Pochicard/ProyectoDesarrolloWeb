package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Usuario;
import com.example.demo.service.ReservaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/barbero")
public class BarberoController {

    private final ReservaService reservaService;

    public BarberoController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public String misReservas(HttpSession sesion, Model model) {
        Usuario barbero = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("reservas", reservaService.findByBarberoId(barbero.getId()));
        return "barbero_reservas";
    }

    @GetMapping("/agenda")
    public String agenda(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                         HttpSession sesion, Model model) {
        Usuario barbero = (Usuario) sesion.getAttribute("usuario");
        LocalDate dia = fecha == null ? LocalDate.now() : fecha;
        model.addAttribute("fecha", dia);
        model.addAttribute("reservas", reservaService.findByBarberoIdAndFecha(barbero.getId(), dia));
        return "barbero_agenda";
    }
}
