package com.bookverse.bookverseApi.service;

import com.bookverse.bookverseApi.config.Config;
import com.bookverse.bookverseApi.dto.SignUpRequestDto;
import com.bookverse.bookverseApi.dto.SignUpResponseDto;
import com.bookverse.bookverseApi.entity.User;
import com.bookverse.bookverseApi.exception.GlobalExceptionHandler;
import com.bookverse.bookverseApi.exception.UserException;
import com.bookverse.bookverseApi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Config config;

    @Override
    public SignUpResponseDto signUp(SignUpRequestDto signUpModel) throws UserException {
        try{
            User existingUser = userRepository.findByUsername(signUpModel.getUsername());
            if(existingUser != null) {
                throw new UserException("User with username " + signUpModel.getUsername() + " already exists.");
            } else {
                User user = config.modelMapper().map(signUpModel, User.class);
                user.setPassword(config.passwordEncoder().encode(signUpModel.getPassword()));
                userRepository.save(user);

                LocalDateTime now = LocalDateTime.now();
                SignUpResponseDto response
                        = SignUpResponseDto.builder()
                        .username(user.getUsername())
                        .createdAt(now)
                        .updatedAt(now)
                        .roles(user.getRoles())
                        .build();

                return response;

            }
        } catch(Exception e) {
            log.error("Error occurred while signing up user: {}", e.getMessage());
            throw new UserException("Something went wrong while signing up user. Please try again later.");
        }

    }
}
