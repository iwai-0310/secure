package io.fullstack.securecapita.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;

//@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    @NotEmpty(message="First name cannot be empty")
    private String firstName;
    @NotEmpty(message="Last name cannot be empty")
    private String lastName;
    @Email(message="Invalid email. Please enter a valid email address")
    @NotEmpty(message="Email cannot be empty")
    private String email;
    @NotEmpty(message="Password cannot be empty")
    private String password;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMfa;
    private LocalDateTime createdAt;
}
