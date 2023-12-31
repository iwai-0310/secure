package io.fullstack.securecapita.repository.implementation;

import io.fullstack.securecapita.domain.Role;
import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.exception.ApiException;
import io.fullstack.securecapita.repository.RoleRepository;

import io.fullstack.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.util.*;

import static io.fullstack.securecapita.enumeration.RoleType.ROLE_USER;
import static io.fullstack.securecapita.enumeration.VerificationType.ACCOUNT;
import static io.fullstack.securecapita.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;
    @Override
    public User create(User user) {
        //check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase())>0) throw new ApiException("Email already in use,Please use different email and try again.");
        //save new user
        try{
            KeyHolder holder=new GeneratedKeyHolder();
            SqlParameterSource parameters= getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY,parameters,holder);
            user.setId(BigInteger.valueOf(requireNonNull(holder.getKey()).longValue()));
            //Add role to the user
//            roleRepository.addRoleToUser(user.getId(),ROLE_USER.name());
            //send verification URL
            String verificationUrl=getVerificationUrl(UUID.randomUUID().toString(),ACCOUNT.getType());
            //save URL in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY,Map.of("userId",user.getId(),"url",verificationUrl));
            //Send email to user with verification URL
            //emailService.sendVerificationUrl(user.getFirstName(),user.getEmail(),verificationUrl,ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            //return the newly created user
            return user;
            //If any errors,throw exception
        }  catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred.Please try again!");
        }
    }


    @Override
    public User get(Long theid) {
        //create the user
        User user=new User();

        try{
            //below line maps the field userID <- key and id <- value
            Map<String,Object> result=jdbc.queryForMap(SELECT_USER_BY_USERID_QUERY,Map.of("userId",theid));
            //check if the result is null or empty
            if(result!=null && !result.isEmpty()){
                //set user details if present.
                Object idValue = result.get("id");
                log.info("Retrieved ID from database: " + idValue);
                user.setId((BigInteger) idValue);
//                log.info(String.valueOf((BigInteger) result.get("id")));
                log.info(result.toString());
                user.setFirstName((String) result.get("first_name"));
                user.setLastName((String) result.get("last_name"));
                user.setEmail((String) result.get("email"));
                //return the use
                return user;
            }else{
                log.warn("User with ID"+theid+"not found");
            }
        }catch(DataAccessException exec) {
            log.error(exec.getMessage());
            throw new ApiException("An error Occurred .Pls try again");
        }
        return user;
    }

    @Override
    public User update(User user) {
        //check the email is present
        if(getEmailCount(user.getEmail().trim().toLowerCase())>0) {
            //save new user
            try{
                SqlParameterSource parameters= getSqlParameterSourceForUpdate(user);
                jdbc.update(UPDATE_USER_QUERY,parameters);
                user.setId(user.getId());
                user.setEnabled(false);
                user.setNotLocked(true);
                //return the newly created user
                return user;
                //If any errors,throw exception
            }  catch (Exception exception){
                log.error(exception.getMessage());
                throw new ApiException("An error occurred.Please try again!");
            }
        }
        return user;
    }
    @Override
    public Boolean delete(Long id) {
        Boolean isDeleted=false;
            try{
                int rowsAffected=jdbc.update(DELETE_USER_BY_USERID_QUERY,Map.of("userId",id));
                if(rowsAffected>0){isDeleted=true;}
            }
            catch (Exception exception){
                log.error(exception.getMessage());
                throw new ApiException("Delete user: user id not found");
            }

        return isDeleted;
    }

    @Override
    public Collection<User> listUsers() {
       List<User> userslist=new ArrayList<>();
        try{
             userslist=jdbc.query(SELECT_ALL_USERS_QUERY,resultSetExtractor);
            return userslist;
        }catch(Exception exc){
            log.error(exc.getMessage());
            throw new ApiException("Something went wrong while returning the Users");
        }
       // return userslist;
    }
    //custom result set extractor
    ResultSetExtractor<List<User>> resultSetExtractor=resultSet->{
        List<User> users = new ArrayList<>();
        while(resultSet.next()){
            User user=new User();
            user.setId(BigInteger.valueOf(resultSet.getLong("id")));
            user.setFirstName( resultSet.getString("first_name"));
            user.setLastName( resultSet.getString("last_name"));
            user.setEmail( resultSet.getString("email"));
            users.add(user);
        }
        return users;
    };


    private Integer  getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email",email),Integer.class);
    }
    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName",user.getFirstName())
                .addValue("lastName",user.getLastName())
                .addValue("email",user.getEmail())
                .addValue("password",encoder.encode(user.getPassword()));

    }
    private SqlParameterSource getSqlParameterSourceForUpdate(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName",user.getFirstName())
                .addValue("lastName",user.getLastName())
                .addValue("email",user.getEmail())
                .addValue("userId",user.getId())
                .addValue("password",encoder.encode(user.getPassword()));

    }
    private String getVerificationUrl(String key,String type){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/"+ type + "/"+key).toUriString();
    }
}
