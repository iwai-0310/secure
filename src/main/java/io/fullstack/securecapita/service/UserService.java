package io.fullstack.securecapita.service;

import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
    UserDTO getUser(Long id);

    UserDTO updateUser(User user);
    Boolean deleteUser(Long id);
}
