package io.fullstack.securecapita.dtomapper;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.dto.RoleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoleDTOMapper {
    public static RoleDTO fromRole(Role role){
        RoleDTO roleDTO=new RoleDTO();
        BeanUtils.copyProperties(role,roleDTO);
        return  roleDTO;
    }
    public static Role toRole(RoleDTO roleDTO){
        Role role=new Role();
        BeanUtils.copyProperties(roleDTO,role);
        return  role;
    }

}
