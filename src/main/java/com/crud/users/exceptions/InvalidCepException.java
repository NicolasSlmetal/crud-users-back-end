package com.crud.users.exceptions;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException(String cep) {
        super("The cep '%s' is invalid".formatted(cep));
    }
}
