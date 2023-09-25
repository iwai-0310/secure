package io.fullstack.securecapita.repository;

import io.fullstack.securecapita.domain.User;

import java.util.Collection;



public interface UserRepository<T extends User> {
    //Basic CRUD operations
    T create(T data);
    Collection<T> list (int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    //Complex Operations
}
