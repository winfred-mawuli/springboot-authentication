package com.restapi.restapi.dto;

import com.restapi.restapi.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private Integer id;
    private String name;
    private String email;
    @NotNull(message = "password is required")
    private String password;
    private Role role = Role.MEMBER;
}
