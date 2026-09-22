package com.example.demo.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Pago;
import com.example.demo.entities.Reserva;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.EspacioService;
import com.example.demo.service.PagoService;
import com.example.demo.service.ReservaService;
import com.example.demo.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final EspacioService espacioService;
    private final PagoService pagoService;

    public ReservaController(ReservaService reservaService, UsuarioService usuarioService,
                             EspacioService espacioService, PagoService pagoService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.espacioService = espacioService;
        this.pagoService = pagoService;
    }

    @GetMapping
    public String mostrarReservas(@RequestParam(required = false) Long usuarioId, Model model) {
        if (usuarioId != null) {
            model.addAttribute("reservas", reservaService.findByUsuarioId(usuarioId));
        } else {
            model.addAttribute("reservas", reservaService.findAll());
        }
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("pagos", pagosPorReserva());
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
        Reserva reserva = reservaService.findById(id);
        if (reserva == null) {
            throw new RecursoNoEncontradoException("No existe una reserva con id " + id);
        }
        model.addAttribute("reserva", reserva);
        model.addAttribute("pago", pagoService.findByReservaId(id));
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
        Reserva reserva = reservaService.findById(id);
        if (reserva == null) {
            throw new RecursoNoEncontradoException("No existe una reserva con id " + id);
        }
        model.addAttribute("reserva", reserva);
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("espacios", espacioService.obtenerTodos());
        return "reserva_form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservaService.deleteById(id);
        return "redirect:/reservas";
    }

    private Map<Long, Pago> pagosPorReserva() {
        return pagoService.findAll().stream()
                .collect(Collectors.toMap(pago -> pago.getReserva().getId(), pago -> pago));
    }
}
