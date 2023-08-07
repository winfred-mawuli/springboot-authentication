package com.restapi.restapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.restapi.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    @NotNull(message = "password is required")
    @JsonIgnore
    private String password;
    private Role role = Role.MEMBER;
}
