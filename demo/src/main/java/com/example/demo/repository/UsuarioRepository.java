package com.example.demo.repository;

import com.example.demo.dto.ResumenRol;
import com.example.demo.entities.Rol;
import com.example.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT new com.example.demo.dto.ResumenRol(u.rol, COUNT(u)) "
            + "FROM Usuario u "
            + "WHERE u.activo = true "
            + "GROUP BY u.rol "
            + "ORDER BY COUNT(u) DESC")
    List<ResumenRol> contarUsuariosPorRol();
}
