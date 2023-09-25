package io.fullstack.securecapita.repository.implementation;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.exception.ApiException;
import io.fullstack.securecapita.repository.RoleRepository;
import io.fullstack.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

import static io.fullstack.securecapita.enumeration.RoleType.ROLE_USER;
import static io.fullstack.securecapita.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    @Override
    public User create(User user) {
        //check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase())>0) throw new ApiException("Email already in use,Please use different email and try again.");
        //save new user
        try{
            KeyHolder holder=new GeneratedKeyHolder();
            SqlParameterSource parameters= getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY,parameters,holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            //Add role to the user
            roleRepository.addRoleToUser(user.getId(),ROLE_USER.name());
        } catch(EmptyResultDataAccessException exception){

        } catch (Exception exception){

        }

        //send verification URL
        //save URL in verification table
        //Send email to user with verification URL
        //return the newly created user
        //If any errors,throw exception with proper message
        return null;
    }




    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
    private Integer  getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email",email),Integer.class);
    }
    private SqlParameterSource getSqlParameterSource(User user) {
        return null;
    }
}
