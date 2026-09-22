package com.example.demo.controller;

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
import com.example.demo.service.PagoService;
import com.example.demo.service.ReservaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;
    private final ReservaService reservaService;

    public PagoController(PagoService pagoService, ReservaService reservaService) {
        this.pagoService = pagoService;
        this.reservaService = reservaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pagos", pagoService.findAll());
        return "pagos";
    }

    @GetMapping("/nuevo")
    public String nuevo(@RequestParam(required = false) Long reservaId, Model model) {
        Pago pago = new Pago();
        if (reservaId != null) {
            Reserva reserva = reservaService.findById(reservaId);
            if (reserva == null) {
                throw new RecursoNoEncontradoException("No existe una reserva con id " + reservaId);
            }
            pago.setReserva(reserva);
            pago.setMonto(reserva.getEspacio().getPrecioBase());
            pago.setEstado("PENDIENTE");
        }
        model.addAttribute("pago", pago);
        model.addAttribute("reservas", reservaService.findAll());
        return "pago_form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("pago") Pago pago, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("reservas", reservaService.findAll());
            return "pago_form";
        }
        pagoService.save(pago);
        return "redirect:/pagos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Pago pago = pagoService.findById(id);
        if (pago == null) {
            throw new RecursoNoEncontradoException("No existe un pago con id " + id);
        }
        model.addAttribute("pago", pago);
        model.addAttribute("reservas", reservaService.findAll());
        return "pago_form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        pagoService.deleteById(id);
        return "redirect:/pagos";
    }
}
