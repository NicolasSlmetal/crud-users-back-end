package com.crud.users.dtos.user;

import com.crud.users.dtos.address.AddressDTO;
import com.crud.users.entities.User;

import java.util.List;

public record CompleteUserDTO (
        Long id,
        String name,
        String email,
        String phone,
        List<AddressDTO> addresses
) {

    public CompleteUserDTO(User user, List<AddressDTO> addresses) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone(), addresses);
    }
}
