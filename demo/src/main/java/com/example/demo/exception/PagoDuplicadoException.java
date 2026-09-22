package com.example.demo.exception;

public class PagoDuplicadoException extends RuntimeException {

    public PagoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
