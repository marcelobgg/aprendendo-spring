package com.marcelo.aprendendospring.infrastructure.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String messagem, Throwable throwable) {
        super(messagem);
    }
}
