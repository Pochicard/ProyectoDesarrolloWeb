package com.example.demo.service;

import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repo.findAll();
    }

    @Override
    public List<Usuario> buscarActivos() {
        return repo.findByActivoTrue();
    }

    @Override
    public List<Usuario> buscarPorRol(Rol rol) {
        return repo.findByRolAndActivoTrue(rol);
    }

    @Override
    public List<Usuario> buscarBarberosDeBarberia(Long barberiaId) {
        return repo.findByBarberiaIdAndRolAndActivoTrue(barberiaId, Rol.BARBERO);
    }

    @Override
    public List<Usuario> buscarTodosLosBarberosDeBarberia(Long barberiaId) {
        return repo.findByBarberiaIdAndRol(barberiaId, Rol.BARBERO);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario autenticar(String correo, String password) {
        return repo.findByCorreoAndPasswordAndActivoTrue(correo, password).orElse(null);
    }

    @Override
    public void registrarCliente(Usuario usuario) {
        usuario.setRol(Rol.CLIENTE);
        usuario.setActivo(true);
        repo.save(usuario);
    }

    @Override
    public void guardar(Usuario usuario) {
        repo.save(usuario);
    }

    @Override
    public boolean existeCorreo(String correo) {
        return repo.findByCorreo(correo).isPresent();
    }

    @Override
    public void desactivar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setActivo(false);
            repo.save(usuario);
        }
    }

    @Override
    public void activar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setActivo(true);
            repo.save(usuario);
        }
    }
}
