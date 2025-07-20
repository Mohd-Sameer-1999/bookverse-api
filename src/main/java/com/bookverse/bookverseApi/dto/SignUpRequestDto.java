package com.bookverse.bookverseApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 letters")
    private String password;

    private List<String> roles;
}
