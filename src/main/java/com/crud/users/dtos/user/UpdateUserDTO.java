package com.crud.users.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
        String name,
        @Email(message = "email is not valid")
        String email,
        @Size(min = 10, max = 12, message = "phone must have size between 10 and 12")
        @Pattern(regexp = "^\\d+$", message = "phone must contain only digits")
        String phone
) {
}
