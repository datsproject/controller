package com.datsddos.controller.app.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ControllerCustomException extends ResponseStatusException {

    public ControllerCustomException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    @Override
    public String toString() {
        return String.format("ControllerCustomException[%s] ", this.getMessage());
    }
}
