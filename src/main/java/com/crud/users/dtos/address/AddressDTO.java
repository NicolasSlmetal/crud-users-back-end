package com.crud.users.dtos.address;

import com.crud.users.dtos.external.ViaCepResponseDTO;
import com.crud.users.entities.Address;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressDTO(
        Long id,
        String cep,
        String street,
        String number,
        String neighborhood,
        String city,
        String state
) {

    public AddressDTO {
        if (cep != null) {
            cep = cep.replaceAll("[^0-9]", "");
        }
    }

    public AddressDTO(Address address) {
        this(address.getId(),
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());
    }

    public AddressDTO(ViaCepResponseDTO viaCepResponseDTO) {
        this(null,
                viaCepResponseDTO.cep(),
                viaCepResponseDTO.logradouro(),
                null,
                viaCepResponseDTO.bairro(),
                viaCepResponseDTO.localidade(),
                viaCepResponseDTO.estado());
    }
}
