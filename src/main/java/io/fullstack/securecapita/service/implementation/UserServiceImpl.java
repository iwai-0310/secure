package io.fullstack.securecapita.service.implementation;

import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.dto.UserDTO;
import io.fullstack.securecapita.dtomapper.UserDTOMapper;
import io.fullstack.securecapita.repository.UserRepository;
import io.fullstack.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional
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
    public Collection<User> list() {
        log.info("Fetching users INFO");
        return userRepository.listUsers();
    }


    @Override
    public UserDTO updateUser(User user) {
        return UserDTOMapper.fromUser(userRepository.update(user));
    }

    @Override
    public Boolean deleteUser(Long id) {
        return userRepository.delete(id);
    }



}
