package com.example.demo.repository;

import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCorreoAndPasswordAndActivoTrue(String correo, String password);

    List<Usuario> findByActivoTrue();

    List<Usuario> findByRol(Rol rol);

    List<Usuario> findByRolAndActivoTrue(Rol rol);

    List<Usuario> findByBarberiaIdAndRolAndActivoTrue(Long barberiaId, Rol rol);

    List<Usuario> findByBarberiaIdAndRol(Long barberiaId, Rol rol);
}
