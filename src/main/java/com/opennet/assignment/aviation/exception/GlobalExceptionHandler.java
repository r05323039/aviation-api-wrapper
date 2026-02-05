package com.opennet.assignment.aviation.exception;

import com.opennet.assignment.aviation.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private ResponseEntity<ApiResponse<Void>> buildResponse(ErrorCode errorCode) {
        return new ResponseEntity<>(
                ApiResponse.error(errorCode),
                errorCode.getHttpStatus()
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodValidation(HandlerMethodValidationException e) {
        Map<String, String> errors = new HashMap<>();

        e.getParameterValidationResults().forEach(result -> {
            String paramName = result.getMethodParameter().getParameterName();
            String message = result.getResolvableErrors().get(0).getDefaultMessage();
            errors.put(paramName, message);
        });

        return new ResponseEntity<>(
                ApiResponse.error(ErrorCode.INVALID_INPUT, errors),
                ErrorCode.INVALID_INPUT.getHttpStatus()
        );
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("Business Exception: {}", e.getErrorCode().getMessage());
        return buildResponse(e.getErrorCode());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception e) {
        return buildResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
