package com.example.demo.service;

import com.example.demo.entities.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> buscarTodos();
    List<Usuario> buscarActivos();
    Usuario buscarPorId(Long id);
    void guardar(Usuario usuario);
    void desactivar(Long id);
    void activar(Long id);
}
