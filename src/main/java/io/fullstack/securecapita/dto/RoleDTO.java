package io.fullstack.securecapita.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
}
