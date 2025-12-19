package com.bookverse.bookverseApi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private int status;
    private Instant timestamp;
    private String traceId;
    private T data;
    private List<ErrorDetail> error;

}
