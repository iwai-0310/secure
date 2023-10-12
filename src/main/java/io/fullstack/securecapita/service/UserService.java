package io.fullstack.securecapita.service;

import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.dto.UserDTO;

import java.util.Collection;

public interface UserService {
    UserDTO createUser(User user);
    User getUser(Long id);
    Collection<User> list();
    UserDTO updateUser(User user);
    Boolean deleteUser(Long id);


}
