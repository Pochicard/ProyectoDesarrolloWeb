package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Usuario;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.buscarTodos());
        return "usuarios";
    }
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "vista_usuario";
    }
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            return "vista_usuario";
        }
        service.guardar(usuario);
        return "redirect:/usuarios";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = service.buscarPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoException("No existe un usuario con id " + id);
        }
        model.addAttribute("usuario", usuario);
        return "vista_usuario";
    }
    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return "redirect:/usuarios";
    }
    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        service.activar(id);
        return "redirect:/usuarios";
    }
}