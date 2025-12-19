package com.bookverse.bookverseApi.util;

import com.bookverse.bookverseApi.dto.ApiResponse;
import com.bookverse.bookverseApi.dto.ErrorDetail;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public class ApiResponses {
    public static <T> ApiResponse<T> ok(String message, T data, String traceId) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .traceId(traceId)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> created(String message, T data, String traceId) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(HttpStatus.CREATED.value())
                .timestamp(Instant.now())
                .traceId(traceId)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, T data, List<ErrorDetail> error, String traceId) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .status(status.value())
                .timestamp(Instant.now())
                .traceId(traceId)
                .data(data)
                .error(error)
                .build();
    }
}
