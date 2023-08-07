package com.restapi.restapi.services;

import com.restapi.restapi.dto.AuthRequestDTO;
import com.restapi.restapi.dto.AuthenticationResponseDTO;
import com.restapi.restapi.dto.RegisterRequestDTO;

public interface AuthService {
    AuthenticationResponseDTO register(RegisterRequestDTO requestDTO);
    AuthenticationResponseDTO authenticate(AuthRequestDTO authRequestDTO);


}
