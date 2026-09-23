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

import com.example.demo.entities.Barberia;
import com.example.demo.entities.Pago;
import com.example.demo.entities.Reserva;
import com.example.demo.entities.Servicio;
import com.example.demo.entities.Usuario;
import com.example.demo.exception.BarberiaInactivaException;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.BarberiaService;
import com.example.demo.service.EspacioService;
import com.example.demo.service.PagoService;
import com.example.demo.service.ReservaService;
import com.example.demo.service.ServicioService;
import com.example.demo.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final BarberiaService barberiaService;
    private final ServicioService servicioService;
    private final EspacioService espacioService;
    private final UsuarioService usuarioService;
    private final ReservaService reservaService;
    private final PagoService pagoService;

    public ClienteController(BarberiaService barberiaService, ServicioService servicioService,
                             EspacioService espacioService, UsuarioService usuarioService,
                             ReservaService reservaService, PagoService pagoService) {
        this.barberiaService = barberiaService;
        this.servicioService = servicioService;
        this.espacioService = espacioService;
        this.usuarioService = usuarioService;
        this.reservaService = reservaService;
        this.pagoService = pagoService;
    }

    @GetMapping
    public String panel(Model model) {
        model.addAttribute("barberias", barberiaService.obtenerActivas());
        return "cliente_barberias";
    }

    @GetMapping("/barberias/{id}")
    public String verBarberia(@PathVariable Long id, Model model) {
        Barberia barberia = barberiaService.obtenerPorId(id);
        if (barberia == null) {
            throw new RecursoNoEncontradoException("No existe una barbería con id " + id);
        }
        verificarBarberiaActiva(barberia);
        model.addAttribute("barberia", barberia);
        model.addAttribute("servicios", servicioService.findByBarberiaId(id));
        model.addAttribute("barberos", usuarioService.buscarBarberosDeBarberia(id));
        return "cliente_barberia";
    }

    @GetMapping("/reservas")
    public String misReservas(HttpSession sesion, Model model) {
        Usuario cliente = usuarioEnSesion(sesion);
        model.addAttribute("reservas", reservaService.findByUsuarioId(cliente.getId()));
        model.addAttribute("pagos", pagosPorReserva());
        return "cliente_reservas";
    }

    @GetMapping("/reservas/nueva")
    public String nuevaReserva(@RequestParam Integer servicioId, HttpSession sesion, Model model) {
        Servicio servicio = servicioService.findById(servicioId);
        if (servicio == null) {
            throw new RecursoNoEncontradoException("No existe un servicio con id " + servicioId);
        }
        verificarBarberiaActiva(servicio.getBarberia());
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuarioEnSesion(sesion));
        reserva.setServicio(servicio);
        model.addAttribute("reserva", reserva);
        cargarOpciones(model, servicio);
        return "cliente_reserva_form";
    }

    @PostMapping("/reservas/guardar")
    public String guardarReserva(@Valid @ModelAttribute("reserva") Reserva reserva,
                                 BindingResult result, HttpSession sesion, Model model) {
        Servicio servicio = servicioService.findById(reserva.getServicio().getId());
        if (servicio == null) {
            throw new RecursoNoEncontradoException("No existe el servicio seleccionado");
        }
        verificarBarberiaActiva(servicio.getBarberia());
        reserva.setUsuario(usuarioEnSesion(sesion));
        reserva.setServicio(servicio);
        if (result.hasErrors()) {
            cargarOpciones(model, servicio);
            return "cliente_reserva_form";
        }
        reservaService.save(reserva);
        return "redirect:/cliente/reservas";
    }

    @GetMapping("/reservas/cancelar/{id}")
    public String cancelarReserva(@PathVariable Long id, HttpSession sesion) {
        Reserva reserva = reservaService.findById(id);
        if (reserva == null || !reserva.getUsuario().getId().equals(usuarioEnSesion(sesion).getId())) {
            throw new RecursoNoEncontradoException("No existe una reserva con id " + id);
        }
        reservaService.deleteById(id);
        return "redirect:/cliente/reservas";
    }

    @GetMapping("/pagos/nuevo")
    public String nuevoPago(@RequestParam Long reservaId, HttpSession sesion, Model model) {
        Reserva reserva = reservaService.findById(reservaId);
        if (reserva == null || !reserva.getUsuario().getId().equals(usuarioEnSesion(sesion).getId())) {
            throw new RecursoNoEncontradoException("No existe una reserva con id " + reservaId);
        }
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setMonto(reserva.getServicio().getPrecio());
        pago.setEstado("PENDIENTE");
        model.addAttribute("pago", pago);
        return "cliente_pago_form";
    }

    @PostMapping("/pagos/guardar")
    public String guardarPago(@Valid @ModelAttribute("pago") Pago pago, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente_pago_form";
        }
        pagoService.save(pago);
        return "redirect:/cliente/reservas";
    }

    private void cargarOpciones(Model model, Servicio servicio) {
        Long barberiaId = servicio.getBarberia().getId();
        model.addAttribute("servicio", servicio);
        model.addAttribute("barberos", usuarioService.buscarBarberosDeBarberia(barberiaId));
        model.addAttribute("espacios", espacioService.obtenerPorBarberia(barberiaId));
    }

    private void verificarBarberiaActiva(Barberia barberia) {
        if (!barberia.getActivo()) {
            throw new BarberiaInactivaException(
                    "La barbería " + barberia.getNombre() + " no está disponible en este momento.");
        }
    }

    private Usuario usuarioEnSesion(HttpSession sesion) {
        return (Usuario) sesion.getAttribute("usuario");
    }

    private Map<Long, Pago> pagosPorReserva() {
        return pagoService.findAll().stream()
                .collect(Collectors.toMap(pago -> pago.getReserva().getId(), pago -> pago));
    }
}
