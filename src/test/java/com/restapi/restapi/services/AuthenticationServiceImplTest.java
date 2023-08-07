package com.restapi.restapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.restapi.dto.AuthenticationResponseDTO;
import com.restapi.restapi.dto.RegisterRequestDTO;
import com.restapi.restapi.enums.Role;
import com.restapi.restapi.models.User;
import com.restapi.restapi.repositories.UserRepository;
import com.restapi.restapi.utils.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private AuthService underTest;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    private ObjectMapper objectMapper = new ObjectMapper();

    //creating setups when test starts
    @BeforeEach
    void setUp() {
        underTest = new AuthenticationServiceImpl(objectMapper,userRepository,passwordEncoder,authenticationManager,jwtService);
    }


    @Test
    void register_ValidRegisterRequest_ReturnsAuthenticationResponseDTO() {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO(19,
                "mawuli",
                "mawuli@gmail.com",
                "password123",
                Role.MEMBER);

        User userEntity = objectMapper.convertValue(requestDTO, User.class);



        //when
        when(userRepository.findUsersByEmail(requestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        when(jwtService.generateToken(userEntity.getEmail())).thenReturn("jwtToken");

        //then
        AuthenticationResponseDTO responseDTO = underTest.register(requestDTO);
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getToken()).isEqualTo("jwtToken");
        verify(userRepository).findUsersByEmail(requestDTO.getEmail());
        verify(passwordEncoder).encode(requestDTO.getPassword());
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(userEntity.getEmail());
    }

    @Test
    @Disabled
    void authenticate() {
    }
}