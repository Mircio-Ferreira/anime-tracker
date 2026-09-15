package org.cesar.demo.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.cesar.demo.backend.enums.UserRole;

public record UserRequest(
        @NotBlank String userName,
        @NotBlank @Email String userLogin,
        @NotBlank String userPassword,
        @NotNull UserRole userRole
) {}
