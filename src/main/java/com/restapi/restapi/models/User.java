package com.restapi.restapi.models;


import com.restapi.restapi.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "user_id",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_sequence")
    private Integer id;

    @NotNull(message = "Name is required")
    @Length(min = 5)
    private String name;

    @Email
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;

}
