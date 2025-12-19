package com.bookverse.bookverseApi.config;

import com.bookverse.bookverseApi.util.TraceId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class TraceIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();

        // Store it in MDC (for logging)
        TraceId.put(traceId);

        try {
            // Add to response header so client sees it
            response.setHeader("X-Trace-Id", traceId);

            // Proceed with the filter chain
            filterChain.doFilter(request, response);
        } finally {
            // Clear after request is complete
            TraceId.clear();
        }
    }
}
