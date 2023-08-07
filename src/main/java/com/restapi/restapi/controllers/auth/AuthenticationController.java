package com.restapi.restapi.controllers.auth;


import com.restapi.restapi.dto.AuthRequestDTO;
import com.restapi.restapi.dto.AuthenticationResponseDTO;
import com.restapi.restapi.dto.RegisterRequestDTO;
import com.restapi.restapi.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(requestDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.authenticate(authRequestDTO));
    }
}
