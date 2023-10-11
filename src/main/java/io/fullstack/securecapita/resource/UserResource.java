package io.fullstack.securecapita.resource;

import io.fullstack.securecapita.domain.HttpResponse;
import io.fullstack.securecapita.domain.User;
import io.fullstack.securecapita.dto.UserDTO;
import io.fullstack.securecapita.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping(path="/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
        UserDTO userDTO=userService.createUser(user);
        return ResponseEntity.created(null).body(
          HttpResponse.builder()
                  .timeStamp(now().toString())
                  .data(Map.of("user",userDTO))
                  .message("User created")
                  .status(HttpStatus.CREATED)
                  .statusCode(HttpStatus.CREATED.value())
                  .build());
    }
    private URI getUri(){
            return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());

    }

    @GetMapping("/Find/{id}")
    public ResponseEntity<HttpResponse> getUser(@PathVariable Long id){
        UserDTO userDTO=userService.getUser(id);
        return ResponseEntity.created(null).body(
        HttpResponse.builder()
                .timeStamp(now().toString())
                .data(Map.of("user",userDTO))
                .message("User presented")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PostMapping("/update")
    public ResponseEntity<HttpResponse> updateUser(@RequestBody @Valid User user){
        UserDTO userDTO=userService.updateUser(user);
        return ResponseEntity.created(null).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("user",userDTO))
                        .message("User updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable Long id){
        Boolean isDeleted=userService.deleteUser(id);
        return ResponseEntity.created(null).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("user-deleted",isDeleted))
                        .message("User deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getUsers() throws InterruptedException {

        return ResponseEntity.created(null).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("users",userService.list()))
                        .message("Users ")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

}
