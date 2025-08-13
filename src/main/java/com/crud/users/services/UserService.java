package com.crud.users.services;

import com.crud.users.dtos.address.AddressDTO;
import com.crud.users.dtos.user.CompleteUserDTO;
import com.crud.users.dtos.user.CreateUserDTO;
import com.crud.users.dtos.user.UpdateUserDTO;
import com.crud.users.dtos.user.UserDTO;
import com.crud.users.entities.User;
import com.crud.users.exceptions.EntityNotFoundException;
import com.crud.users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    public CompleteUserDTO findById(Long id) {
        User user = fetchUserOrThrowErrorIfNotFound(id);
        List<AddressDTO> addressesDTOS = transformUserAddressesToDTO(user);
        return new CompleteUserDTO(user, addressesDTOS);
    }

    public User fetchUserOrThrowErrorIfNotFound(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private static List<AddressDTO> transformUserAddressesToDTO(User user) {
        return Optional.ofNullable(user.getAddresses())
                .map(list -> list.stream().map(AddressDTO::new).toList())
                .orElse(List.of());
    }

    public CompleteUserDTO createUser(CreateUserDTO userDTO) {
        User user = new User(userDTO);
        User savedUser = userRepository.save(user);
        List<AddressDTO> addressDTOS = transformUserAddressesToDTO(savedUser);
        return new CompleteUserDTO(savedUser, addressDTOS);
    }

    public CompleteUserDTO updateUser(Long id, UpdateUserDTO userDTO) {
        User user = fetchUserOrThrowErrorIfNotFound(id);
        String name = userDTO.name();
        String email = userDTO.email();
        String phone = userDTO.phone();
        if (name != null && !name.isBlank()) {
            user.setName(name);
        }

        if (email != null && !email.isBlank()) {
            user.setEmail(email);
        }

        if (phone != null && !phone.isBlank()) {
            user.setPhone(phone);
        }

        User updatedUser = userRepository.save(user);
        List<AddressDTO> addressesDTOS = transformUserAddressesToDTO(user);
        return new CompleteUserDTO(updatedUser, addressesDTOS);
    }

    public void deleteUser(Long id) {
        User user = fetchUserOrThrowErrorIfNotFound(id);
        userRepository.delete(user);
    }
}
