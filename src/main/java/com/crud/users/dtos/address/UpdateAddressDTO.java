package com.crud.users.dtos.address;

import jakarta.validation.constraints.Size;

public record UpdateAddressDTO(
        @Size(min = 8, max = 8, message = "cep must have length equals to 8")
        String cep,
        String street,
        String state,
        String city,
        String number,
        String neighborhood
) {
}
