package com.crud.users.dtos.user;

import com.crud.users.entities.User;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone
)  {

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
