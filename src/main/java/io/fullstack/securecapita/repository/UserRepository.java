package io.fullstack.securecapita.repository;

import io.fullstack.securecapita.domain.User;

import java.util.Collection;



public interface UserRepository<T extends User> {
    //Basic CRUD operations
    T create(T data);
    T get(Long id);
    T update(User user);
    Boolean delete(Long id);
    Collection<T> listUsers(Long limit);

    //Complex Operations
}
