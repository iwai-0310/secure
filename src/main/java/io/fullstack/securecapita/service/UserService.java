package io.fullstack.securecapita.service;

import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
