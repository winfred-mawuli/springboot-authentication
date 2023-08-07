package com.restapi.restapi.services;

import com.restapi.restapi.enums.Role;
import com.restapi.restapi.exceptions.UserException;
import com.restapi.restapi.models.User;
import com.restapi.restapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    //creating a mock service to mimic that of an actual object
    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    //creating setups when test starts
    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }


    @Test
    void canCreateUser() {
        //given
        User user = new User(
                19,
                "Jamila",
                "jamila@gmail.com",
                "password123",
                Role.MEMBER
        );

        //when
        underTest.creatUser(user);

        //then
        //capture the arguments that was passed to the user class
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        // verify to check if the save method was invoked and capture the value
        verify(userRepository).save(userArgumentCaptor.capture());
        //get value of  the capturedArgument
        User capturedUser = userArgumentCaptor.getValue();
        //check if the captured user is same as the argument passed
        assertThat(capturedUser).isEqualTo(user);
    }


    @Test
    void WIllThrowWhenEmailIsTaken() {
        //given
        User user = new User(
                19,
                "Jamila",
                "jamila@gmail.com",
                "password123",
                Role.MEMBER
        );

        given(userRepository.findUsersByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        //when
        //then
        assertThatThrownBy(() -> underTest.creatUser(user))
                .isInstanceOf(UserException.class)
                .hasMessage("Email is already in use");

        verify(userRepository, never()).save(any());
    }

    @Test
    void canGetAllUsers() {
        //when
        underTest.getUsers();
        //then
        verify(userRepository).findAll();
    }
}