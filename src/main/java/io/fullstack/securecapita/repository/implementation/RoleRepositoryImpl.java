package io.fullstack.securecapita.repository.implementation;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.exception.ApiException;
import io.fullstack.securecapita.repository.RoleRepository;
import io.fullstack.securecapita.rowMapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static io.fullstack.securecapita.enumeration.RoleType.ROLE_USER;
import static io.fullstack.securecapita.query.RoleQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {


    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        Role role=new Role();
        try{
            role= jdbc.queryForObject(SELECT_ROLE_BY_ROLEID_QUERY,Map.of("roleId",id),new RoleRowMapper());
            return role;
        }
        catch(EmptyResultDataAccessException exception){
            throw new ApiException("No role found by id:"+id);
        }
        catch (Exception exc){
            log.error(exc.getMessage());
        }
        return role;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}",roleName,userId);
        try{
            Role role=jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY,Map.of("Role_name",roleName),new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId",userId,"roleId", Objects.requireNonNull(role).getId()));
        } catch(EmptyResultDataAccessException exception){
            throw new ApiException("No role found by name:"+ROLE_USER.name());
        } catch (Exception exception){
            throw new ApiException("An error occurred.Please try again!");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
