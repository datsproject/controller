package com.datsddos.controller.app.exception.core;

import com.datsddos.controller.app.exception.custom.ControllerCustomException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleDbException(IllegalArgumentException exception) {
        logger.error("IllegalArgumentException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        return generateErrorResponse(ErrorCode.INT_REQUEST, message, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UndeclaredThrowableException.class)
    public ErrorResponse handleUndeclaredThrowableException(UndeclaredThrowableException exception) {
        logger.error("UndeclaredThrowableException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        return generateErrorResponse(ErrorCode.INT_REQUEST, message, null);
    }

    @ExceptionHandler(ControllerCustomException.class)
    public ErrorResponse handleControllerCustomException(ControllerCustomException exception, HttpServletResponse response) {
        logger.error("ControllerCustomException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        StackTraceElement[] stacktrace = NestedExceptionUtils.getMostSpecificCause(exception).getStackTrace();
        response.setStatus(exception.getStatus().value());
        return generateErrorResponse(ErrorCode.CONTROLLER_CUSTOM_EXCEPTION, message, null, null);
    }

    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ExceptionHandler(MqttException.class)
    public ErrorResponse handleMqttException(MqttException exception) {
        logger.error("MqttException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        return generateErrorResponse(ErrorCode.MQTT_MESSAGE_BROKER_EXCEPTION, message, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException exception) {
        logger.error("MethodArgumentNotValidException: ", exception);
        Map<String, String> errors = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            String field = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage() == null ? "Unknown" : objectError.getDefaultMessage();
            errors.put(field, errorMessage);
            sb.append(field).append(" ").append(errorMessage);
        }

        return generateErrorResponse(ErrorCode.INT_REQUEST, sb.toString(), errors);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorResponse handleEmptyResultDataException(EmptyResultDataAccessException exception) {
        logger.error("EmptyResultDataAccessException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        return generateErrorResponse(ErrorCode.INT_EMPTY, message, null);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ErrorResponse handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        logger.error("MissingRequestHeaderException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        return generateErrorResponse(ErrorCode.INT_REQUEST_MISSING_HEADER, message, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerWebInputException.class)
    public ErrorResponse handleServerWebInputException(ServerWebInputException exception) {
        logger.error("ServerWebInputException", exception);
        String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
        return generateErrorResponse(ErrorCode.INT_REQUEST, message, null);
    }


    private ErrorResponse generateErrorResponse(ErrorCode errorCode, String systemMessage, Map<String, String> errors, String stackTrace) {
        String errorMessage = systemMessage == null ? "Error not defined" : systemMessage;
        String errCode = errorCode.getCategoryCode() + errorCode.getSpecificCode();
        return new ErrorResponse(errorCode.getMessage(), errCode, errorMessage, errors, stackTrace);
    }

    private ErrorResponse generateErrorResponse(ErrorCode errorCode, String systemMessage, Map<String, String> errors) {
        String errorMessage = systemMessage == null ? "Error not defined" : systemMessage;
        String errCode = errorCode.getCategoryCode() + errorCode.getSpecificCode();
        return new ErrorResponse(errorCode.getMessage(), errCode, errorMessage, errors, "");
    }
}

