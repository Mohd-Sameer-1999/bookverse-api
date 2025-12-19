package com.bookverse.bookverseApi.exception;

import com.bookverse.bookverseApi.dto.ApiResponse;
import com.bookverse.bookverseApi.dto.ErrorDetail;
import com.bookverse.bookverseApi.util.ApiResponses;
import com.bookverse.bookverseApi.util.TraceId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundExcetion.class)
    public ResponseEntity<String> handleBookNotFoundExcetion(BookNotFoundExcetion e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e){
//        Map<String, String> errors = new HashMap<>();
//        e.getBindingResult().getFieldErrors().forEach(err -> {
//            errors.put(err.getField(), err.getDefaultMessage());
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ErrorDetail>>> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .toList();

        ApiResponse<List<ErrorDetail>> body =
                ApiResponses.error(HttpStatus.BAD_REQUEST, "Validation failed", null, details, TraceId.current());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<List<ErrorDetail>>> handleForbidden(AccessDeniedException ex) {
        List<ErrorDetail> details = List.of(new ErrorDetail("", "Access denied"));

        ApiResponse<List<ErrorDetail>> body =
                ApiResponses.error(HttpStatus.FORBIDDEN, "Access denied", null, details, TraceId.current());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<List<ErrorDetail>>> handleAuth(AuthenticationException ex) {
        List<ErrorDetail> details = List.of(new ErrorDetail("", "Unauthorized"));

        ApiResponse<List<ErrorDetail>> body =
                ApiResponses.error(HttpStatus.UNAUTHORIZED, "Unauthorized", null, details, TraceId.current());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<List<ErrorDetail>>> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        List<ErrorDetail> details = List.of(new ErrorDetail("", "Internal server error"));

        ApiResponse<List<ErrorDetail>> body =
                ApiResponses.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", null, details, TraceId.current());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
