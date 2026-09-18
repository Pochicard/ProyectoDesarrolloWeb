package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre no puede contener números ni caracteres especiales")
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[cC][oO][mM]$", message = "El correo debe tener un formato válido y terminar en .com")
    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono solo debe contener números (entre 7 y 15 dígitos)")
    @Column(length = 30)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;
}