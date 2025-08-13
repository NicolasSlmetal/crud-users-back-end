package com.crud.users.controllers;

import com.crud.users.dtos.address.AddressDTO;
import com.crud.users.dtos.address.CreateAddressDTO;
import com.crud.users.dtos.address.UpdateAddressDTO;
import com.crud.users.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class AddressController extends BaseUserController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<AddressDTO> findAddressById(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok(addressService.findAddressById(addressId));
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<AddressDTO> createAddress(@PathVariable("id") Long userId,
                                                    @RequestBody @Valid CreateAddressDTO addressDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, userId);
        UriComponents uriComponents = uriComponentsBuilder
                .path("/{userId}/addresses/{addressId}")
                .buildAndExpand(userId, savedAddressDTO.id());
        return ResponseEntity.created(uriComponents.toUri()).body(savedAddressDTO);
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("addressId") Long addressId,
                                                    @RequestBody @Valid UpdateAddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.updateAddress(addressDTO, addressId));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

}
