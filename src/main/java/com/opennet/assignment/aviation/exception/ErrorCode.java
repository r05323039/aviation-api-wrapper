package com.opennet.assignment.aviation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(200, "Success", HttpStatus.OK),

    INVALID_INPUT(400, "Invalid Input Parameters", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(404, "Resource Not Found", HttpStatus.NOT_FOUND),

    INTERNAL_SERVER_ERROR(500, "Unexpected Internal Error", HttpStatus.INTERNAL_SERVER_ERROR),
    UPSTREAM_SERVICE_UNAVAILABLE(503, "Upstream Service Unavailable", HttpStatus.SERVICE_UNAVAILABLE);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}