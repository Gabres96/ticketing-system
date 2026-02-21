package com.gabriel.ticketing.auth;

import com.gabriel.ticketing.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotBlank String login, @NotBlank String password, @NotNull UserRole role) {
}