package com.datsddos.controller.app.exception.core;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INT_REQUEST("01", "01", "Invalid controller request exception"),
    INT_EMPTY("01", "02", "N/A Data"),
    INT_UNKNOWN("01", "03", "Unknown exception occurred."),
    INT_REQUEST_BODY_MISSING("01", "04", "Request body is missing"),
    INT_REQUEST_MISSING_HEADER("01", "05", "Request header is missing"),
    CONTROLLER_CUSTOM_EXCEPTION("01", "06", "Controller Custom Exception"),
    MQTT_MESSAGE_BROKER_EXCEPTION("01", "07", "Mqtt MessageBroker Exception");


    private final String categoryCode;
    private final String specificCode;
    private final String message;

    ErrorCode(String categoryCode, String specificCode, String message) {

        this.categoryCode = categoryCode;
        this.specificCode = specificCode;
        this.message = message;
    }
}
