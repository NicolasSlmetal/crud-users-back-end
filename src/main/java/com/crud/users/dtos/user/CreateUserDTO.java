package com.crud.users.dtos.user;

import jakarta.validation.constraints.*;


public record CreateUserDTO (
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "email is required")
        @Email(message = "email is not valid")
        String email,
        @NotBlank(message = "phone is required")
        @Pattern(regexp = "^\\d+$", message = "phone must contain only digits")
        @Size(min=10, max = 12, message = "phone must have size between 10 and 12")
        String phone
) {
}
