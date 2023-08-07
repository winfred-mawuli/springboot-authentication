package com.restapi.restapi.services;

import com.restapi.restapi.dto.UserDTO;
import com.restapi.restapi.models.User;


import java.util.List;

public interface UserService {
    User creatUser(User user);
    List<UserDTO> getUsers();
}
