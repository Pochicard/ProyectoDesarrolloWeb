package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Rol;
import com.example.demo.service.BarberiaService;
import com.example.demo.service.ReservaService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BarberiaService barberiaService;
    private final UsuarioService usuarioService;
    private final ServicioService servicioService;
    private final ReservaService reservaService;

    public AdminController(BarberiaService barberiaService, UsuarioService usuarioService,
                           ServicioService servicioService, ReservaService reservaService) {
        this.barberiaService = barberiaService;
        this.usuarioService = usuarioService;
        this.servicioService = servicioService;
        this.reservaService = reservaService;
    }

    @GetMapping
    public String panel(Model model) {
        model.addAttribute("totalBarberias", barberiaService.obtenerTodas().size());
        model.addAttribute("totalUsuarios", usuarioService.buscarTodos().size());
        model.addAttribute("totalBarberos", usuarioService.buscarPorRol(Rol.BARBERO).size());
        model.addAttribute("totalServicios", servicioService.findAll().size());
        model.addAttribute("totalReservas", reservaService.findAll().size());
        return "admin_panel";
    }
}
