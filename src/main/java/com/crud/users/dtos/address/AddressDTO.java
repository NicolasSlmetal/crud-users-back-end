package com.crud.users.dtos.address;

import com.crud.users.entities.Address;

public record AddressDTO(
        Long id,
        String cep,
        String street,
        String number,
        String state,
        String city,
        String neighborhood
) {

    public AddressDTO(Address address) {
        this(address.getId(),
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getState(),
                address.getCity(),
                address.getNeighborhood());
    }
}
