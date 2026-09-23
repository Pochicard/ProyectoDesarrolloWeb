package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio(HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            return "redirect:/admin";
        }
        if (usuario.getRol() == Rol.GERENTE) {
            return "redirect:/gerente";
        }
        if (usuario.getRol() == Rol.BARBERO) {
            return "redirect:/barbero";
        }
        return "redirect:/cliente";
    }
}
