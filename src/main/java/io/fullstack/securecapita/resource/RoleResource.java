package io.fullstack.securecapita.resource;

import io.fullstack.securecapita.domain.HttpResponse;
import io.fullstack.securecapita.dto.RoleDTO;
import io.fullstack.securecapita.dto.UserDTO;
import io.fullstack.securecapita.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/role")
public class RoleResource {
    private RoleService roleService;
    @GetMapping("/Find/{id}")
    public ResponseEntity<HttpResponse> getRole(@PathVariable Long id){

        RoleDTO roleDTO=roleService.getRole(id);
        return ResponseEntity.created(null).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("role",roleDTO))
                        .message("Role present")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
