package com.datsddos.controller.app.exception.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String description;
    String errCode;
    String message;
    Map<String, String> errors;
    String stackTrace;
}
