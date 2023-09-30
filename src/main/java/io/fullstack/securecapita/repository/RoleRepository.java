package io.fullstack.securecapita.repository;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.domain.User;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    //Basic CRUD operations
    T create(T data);
    Collection<T> list (int page, int pageSize);
     T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    //Complex Operations
    void addRoleToUser(Long userId,String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId,String roleName);
}

