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

import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.BarberiaService;
import com.example.demo.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final BarberiaService barberiaService;

    public UsuarioController(UsuarioService service, BarberiaService barberiaService) {
        this.service = service;
        this.barberiaService = barberiaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Rol rol, Model model) {
        if (rol != null) {
            model.addAttribute("usuarios", service.buscarTodosPorRol(rol));
        } else {
            model.addAttribute("usuarios", service.buscarTodos());
        }
        model.addAttribute("rolFiltro", rol);
        model.addAttribute("roles", Rol.values());
        model.addAttribute("resumenRoles", service.contarUsuariosPorRol());
        return "usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        cargarOpciones(model);
        return "vista_usuario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
                          BindingResult result,
                          Model model) {
        if (usuario.getBarberia() != null && usuario.getBarberia().getId() == null) {
            usuario.setBarberia(null);
        }
        if (usuario.getRol() == Rol.BARBERO || usuario.getRol() == Rol.GERENTE) {
            if (usuario.getBarberia() == null) {
                result.rejectValue("barberia", "barberia.requerida",
                        "Debes asignar una barbería a los barberos y gerentes");
            }
        } else {
            usuario.setBarberia(null);
        }
        if (result.hasErrors()) {
            cargarOpciones(model);
            return "vista_usuario";
        }
        service.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = service.buscarPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoException("No existe un usuario con id " + id);
        }
        model.addAttribute("usuario", usuario);
        cargarOpciones(model);
        return "vista_usuario";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        service.activar(id);
        return "redirect:/admin/usuarios";
    }

    private void cargarOpciones(Model model) {
        model.addAttribute("roles", Rol.values());
        model.addAttribute("barberias", barberiaService.obtenerActivas());
    }
}
