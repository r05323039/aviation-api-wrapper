package com.opennet.assignment.aviation.dto;

import com.opennet.assignment.aviation.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;


    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> error(ErrorCode errorCode, T errorDetails) {
        return ApiResponse.<T>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .data(errorDetails)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("Success")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}