package com.bookverse.bookverseApi.controller.v1;

import com.bookverse.bookverseApi.config.auth.JwtUtil;
import com.bookverse.bookverseApi.dto.*;
import com.bookverse.bookverseApi.service.AuthServiceImpl;
import com.bookverse.bookverseApi.util.ApiResponses;
import com.bookverse.bookverseApi.util.TraceId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponseDto>> signUp(@RequestBody @Valid SignUpRequestDto signUpModel) {
        SignUpResponseDto result = authService.signUp(signUpModel);

        ApiResponse<SignUpResponseDto> response = ApiResponses.created("User signed up successful", result, TraceId.current());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));


        UserDetails userDetails =   userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        ApiResponse<String> response = ApiResponses.ok("Login successful", jwt, TraceId.current());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
