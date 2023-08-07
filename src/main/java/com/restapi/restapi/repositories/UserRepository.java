package com.restapi.restapi.repositories;

import com.restapi.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer> {

    Optional<User> findUsersByEmail(String username);
}
