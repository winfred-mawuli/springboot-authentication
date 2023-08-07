package com.restapi.restapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.restapi.dto.AuthRequestDTO;
import com.restapi.restapi.dto.AuthenticationResponseDTO;
import com.restapi.restapi.dto.RegisterRequestDTO;
import com.restapi.restapi.dto.UserDTO;
import com.restapi.restapi.exceptions.UserException;
import com.restapi.restapi.models.User;
import com.restapi.restapi.repositories.UserRepository;
import com.restapi.restapi.utils.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthService {
    ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        userRepository.findUsersByEmail(requestDTO.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserException("Email already in use");
                });
        User userEntity = objectMapper.convertValue(requestDTO, User.class);
        userEntity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(userEntity);
        var jwtToken = jwtService.generateToken(userEntity.getEmail());
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthRequestDTO authRequestDTO) {

        //authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                )
        );

        var user = userRepository.findUsersByEmail(authRequestDTO.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user.getEmail());
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }


}
