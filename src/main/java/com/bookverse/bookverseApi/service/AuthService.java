package com.bookverse.bookverseApi.service;

import com.bookverse.bookverseApi.dto.SignUpRequestDto;
import com.bookverse.bookverseApi.dto.SignUpResponseDto;

public interface AuthService {

    SignUpResponseDto signUp(SignUpRequestDto signUpModel);

}
