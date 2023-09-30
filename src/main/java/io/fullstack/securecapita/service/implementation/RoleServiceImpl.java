package io.fullstack.securecapita.service.implementation;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.dto.RoleDTO;
import io.fullstack.securecapita.dtomapper.RoleDTOMapper;
import io.fullstack.securecapita.repository.RoleRepository;
import io.fullstack.securecapita.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Role> roleRepository;
    @Override
    public RoleDTO createRole(Role role) {
        return null;
    }

    @Override
    public RoleDTO getRole(Long id) {
        return RoleDTOMapper.fromRole(roleRepository.get(id));
    }

    @Override
    public RoleDTO updateRole(Role role) {
        return null;
    }

    @Override
    public RoleDTO deleteRole(Long id) {
        return null;
    }
}
