package io.fullstack.securecapita.service.implementation;

import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.dto.UserDTO;
import io.fullstack.securecapita.dtomapper.UserDTOMapper;
import io.fullstack.securecapita.repository.UserRepository;
import io.fullstack.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }

    @Override
    public UserDTO getUser(Long id) {
        return UserDTOMapper.fromUser(userRepository.get(id));
    }

    @Override
    public UserDTO updateUser(User user) {
        return UserDTOMapper.fromUser(userRepository.update(user));
    }

}
