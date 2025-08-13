package com.crud.users.dtos.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAddressDTO(
        @NotBlank(message = "cep must be provided")
        @Size(min = 8, max = 8, message = "cep must have length equals to 8")
        @Pattern(regexp = "^^\\d+$", message = "cep must contain only digits")
        String cep,
        @NotBlank(message = "street must be provided") String street,
        @NotBlank(message = "state must be provided") String state,
        @NotBlank(message = "city must be provided") String city,
        @NotBlank(message = "number must be provided") String number,
        @NotBlank(message = "neighborhood must be provided") String neighborhood
) {
}
