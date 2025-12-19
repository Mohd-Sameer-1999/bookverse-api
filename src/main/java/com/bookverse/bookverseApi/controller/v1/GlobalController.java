package com.bookverse.bookverseApi.controller.v1;

import com.bookverse.bookverseApi.dto.ApiResponse;
import com.bookverse.bookverseApi.util.ApiResponses;
import com.bookverse.bookverseApi.util.TraceId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/global")
public class GlobalController {

    @RequestMapping("/health-check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        ApiResponse<String> response = ApiResponses.ok("Healthy", null, TraceId.current());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
