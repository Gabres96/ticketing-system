package com.gabriel.ticketing.exception;

public class InvalidEventDateException extends BusinessException {

    public InvalidEventDateException() {
        super("Event date cannot be in the past");
    }
}
