package com.bookverse.bookverseApi.controller;

import com.bookverse.bookverseApi.dto.SignUpRequestDto;
import com.bookverse.bookverseApi.dto.SignUpResponseDto;
import com.bookverse.bookverseApi.service.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpModel) {
        SignUpResponseDto result = authService.signUp(signUpModel);
        return ResponseEntity.ok(result);

    }
}
