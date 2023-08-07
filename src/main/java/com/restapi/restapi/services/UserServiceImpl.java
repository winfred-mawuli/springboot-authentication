package com.restapi.restapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.restapi.dto.UserDTO;
import com.restapi.restapi.exceptions.UserException;
import com.restapi.restapi.models.User;
import com.restapi.restapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    ObjectMapper objectMapper = new ObjectMapper();

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User creatUser(User user) {
            Optional<User> exists = userRepository.findUsersByEmail(user.getEmail());
            if (exists.isPresent()){
                throw new UserException("Email is already in use");
            }
            User userEntity = objectMapper.convertValue(user, User.class);
            return userRepository.save(userEntity);

    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> userEntityList=  userRepository.findAll();
        return userEntityList.stream().map(user -> objectMapper.convertValue(user, UserDTO.class)).toList();
    }
}
