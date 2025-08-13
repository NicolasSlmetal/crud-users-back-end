package com.crud.users.controllers;


import java.util.List;

import com.crud.users.dtos.user.CompleteUserDTO;
import com.crud.users.dtos.user.CreateUserDTO;
import com.crud.users.dtos.user.UpdateUserDTO;
import com.crud.users.dtos.user.UserDTO;
import com.crud.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserController extends BaseUserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompleteUserDTO> findUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompleteUserDTO> createUser(@RequestBody @Valid CreateUserDTO userDTO,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        CompleteUserDTO savedUserDTO = userService.createUser(userDTO);

        UriComponents uriComponents = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(savedUserDTO.id());
        return ResponseEntity.created(uriComponents.toUri()).body(savedUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompleteUserDTO> updateUser(@PathVariable(name = "id") Long id,
                                                      @RequestBody @Valid UpdateUserDTO userDTO) {
        CompleteUserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
