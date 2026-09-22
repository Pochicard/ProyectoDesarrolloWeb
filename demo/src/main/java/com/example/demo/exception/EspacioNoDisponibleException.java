package com.example.demo.exception;

public class EspacioNoDisponibleException extends RuntimeException {

    public EspacioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
