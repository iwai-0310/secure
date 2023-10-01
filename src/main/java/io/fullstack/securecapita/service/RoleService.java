package io.fullstack.securecapita.service;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.dto.RoleDTO;

public interface RoleService {
    RoleDTO createRole(Role role);

    RoleDTO getRole(Long id);
    RoleDTO updateRole(Role role);

    RoleDTO deleteRole(Long id);

    RoleDTO getUserRole(Long id);
}
