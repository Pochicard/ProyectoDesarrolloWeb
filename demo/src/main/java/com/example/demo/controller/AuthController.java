package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;
import com.example.demo.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin(HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        if (usuario != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, @RequestParam String password,
                                HttpSession sesion, Model model) {
        Usuario usuario = usuarioService.autenticar(correo, password);
        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            model.addAttribute("correo", correo);
            return "login";
        }
        sesion.setAttribute("usuario", usuario);
        return "redirect:/";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        Usuario usuario = new Usuario();
        usuario.setRol(Rol.CLIENTE);
        model.addAttribute("usuario", usuario);
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro";
        }
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "Ya existe una cuenta registrada con ese correo.");
            return "registro";
        }
        usuarioService.registrarCliente(usuario);
        return "redirect:/login?registrado";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession sesion) {
        sesion.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado(Model model) {
        model.addAttribute("mensaje", "No tienes permisos para acceder a esa sección.");
        return "error";
    }
}
