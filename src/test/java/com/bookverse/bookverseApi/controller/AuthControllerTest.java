package com.bookverse.bookverseApi.controller;

import com.bookverse.bookverseApi.dto.SignUpRequestDto;
import com.bookverse.bookverseApi.dto.SignUpResponseDto;
import com.bookverse.bookverseApi.service.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthServiceImpl authService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testSignUp_Success() throws Exception {

        // Arrange
        SignUpRequestDto requestDto =
                new SignUpRequestDto("testuser", "password123", List.of("USER"));

        SignUpResponseDto responseDto = SignUpResponseDto.builder()
                .username(requestDto.getUsername())
                .updatedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .roles(requestDto.getRoles())
                .build();

        when(authService.signUp(any(SignUpRequestDto.class)))
                .thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value("User signed up successful"));
    }

//    @Test
//    void testSignUp_Success() throws Exception {
//        // Arrange
//        SignUpRequestDto requestDto = new SignUpRequestDto("testuser", "password123", Arrays.asList("USER", "ADMIN"));
//
//        Mockito.when(authService.signUp(any(SignUpRequestDto.class))).thenReturn(signUpResponseDto);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/auth/sign-up")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.message").value("User signed up successful"));
//    }
//
    @Test
    void testSignUp_InvalidRequest() throws Exception {
        // Arrange
        SignUpRequestDto requestDto = new SignUpRequestDto("", "password123", null);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void testSignUp_ExistingUsername() throws Exception {
        // Arrange
        SignUpRequestDto requestDto = new SignUpRequestDto("testuser", "password123", Arrays.asList("USER", "ADMIN"));
        Mockito.when(authService.signUp(any(SignUpRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Username already exists"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }
}