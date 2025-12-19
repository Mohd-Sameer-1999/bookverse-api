package com.bookverse.bookverseApi.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiDeprecationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().startsWith("/api/v1")){
            response.setHeader("X-API-Deprecated", "true");
            response.setHeader("X-API-SunSet", "31-12-2025");
        }

        return true;
    }
}
