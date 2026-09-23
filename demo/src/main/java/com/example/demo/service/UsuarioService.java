package com.example.demo.service;

import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> buscarTodos();
    List<Usuario> buscarActivos();
    List<Usuario> buscarPorRol(Rol rol);
    List<Usuario> buscarBarberosDeBarberia(Long barberiaId);
    List<Usuario> buscarTodosLosBarberosDeBarberia(Long barberiaId);
    Usuario buscarPorId(Long id);
    Usuario autenticar(String correo, String password);
    void registrarCliente(Usuario usuario);
    void guardar(Usuario usuario);
    boolean existeCorreo(String correo);
    void desactivar(Long id);
    void activar(Long id);
}
