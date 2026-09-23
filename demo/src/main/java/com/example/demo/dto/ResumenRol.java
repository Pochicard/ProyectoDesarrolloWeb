package com.example.demo.dto;

import com.example.demo.entities.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResumenRol {

    private Rol rol;

    private Long total;
}
