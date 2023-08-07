package com.restapi.restapi.repositories;



import com.restapi.restapi.enums.Role;
import com.restapi.restapi.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Autowired
    private UserRepository underTest;
    @Test
    void shouldCheckWhenEmailExist() {
         //given
        String email = "jamila@gmail.com";
        User user = new User(
                19,
                "Jamila",
                email,
                "password123",
                Role.MEMBER
        );

        underTest.save(user);
        //when
        Optional<User> expected = underTest.findUsersByEmail(email);
        //then
        assertThat(expected).isPresent();
    }

    @Test
    void shouldCheckEmailDoesNotExist() {
        //given
        String email = "jamila@gmail.com";
        //when
        Optional<User> expected = underTest.findUsersByEmail(email);
        //then
        assertThat(expected).isEmpty();
    }
}