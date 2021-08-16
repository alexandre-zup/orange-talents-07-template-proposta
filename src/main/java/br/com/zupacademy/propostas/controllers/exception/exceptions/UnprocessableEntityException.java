package br.com.zupacademy.propostas.controllers.exception.exceptions;

public class UnprocessableEntityException extends Exception {
    private String message;

    public UnprocessableEntityException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
