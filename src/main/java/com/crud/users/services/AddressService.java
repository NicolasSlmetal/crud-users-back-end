package com.crud.users.services;

import com.crud.users.dtos.address.AddressDTO;
import com.crud.users.dtos.address.CreateAddressDTO;
import com.crud.users.dtos.address.UpdateAddressDTO;
import com.crud.users.dtos.external.ViaCepResponseDTO;
import com.crud.users.entities.Address;
import com.crud.users.entities.User;
import com.crud.users.exceptions.EntityNotFoundException;
import com.crud.users.exceptions.InvalidCepException;
import com.crud.users.repositories.AddressRepository;
import com.crud.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ApiRequestService apiRequestService;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository, UserService userService, ApiRequestService apiRequestService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.apiRequestService = apiRequestService;
    }

    public AddressDTO findAddressById(Long id) {
        Address address = fetchAddressOrThrowErrorIfNotFound(id);
        return new AddressDTO(address);
    }

    private Address fetchAddressOrThrowErrorIfNotFound(Long id) {
        return addressRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    public AddressDTO createAddress(CreateAddressDTO addressDTO, Long userId) {
        validateCEP(addressDTO.cep());
        User user = userService.fetchUserOrThrowErrorIfNotFound(userId);
        Address address = new Address(addressDTO);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return new AddressDTO(savedAddress);
    }

    public void validateCEP(String cep) {
        String url = "https://viacep.com.br/ws/{cep}/json/".replace("{cep}", cep);
        InvalidCepException invalidCepException = new InvalidCepException(cep);
        ViaCepResponseDTO response = apiRequestService.fetchAndMapForType(url, ViaCepResponseDTO.class, invalidCepException);

        String error = response.erro();
        if (error != null && error.equals("true")) {
            throw invalidCepException;
        }
    }

    public AddressDTO updateAddress(UpdateAddressDTO addressDTO, Long id) {
        Address address = fetchAddressOrThrowErrorIfNotFound(id);

        String cep = addressDTO.cep();
        String street = addressDTO.street();
        String number = addressDTO.number();
        String neighborhood = addressDTO.neighborhood();
        String city = addressDTO.city();
        String state = addressDTO.state();

        if (cep != null) {
            validateCEP(cep);
            address.setCep(cep);
        }

        if (street != null) {
            address.setStreet(street);
        }

        if (number != null) {
            address.setNumber(number);
        }

        if (neighborhood != null) {
            address.setNeighborhood(neighborhood);
        }

        if (city != null) {
            address.setCity(city);
        }

        if (state != null) {
            address.setState(state);
        }

        Address updatedAddress = addressRepository.save(address);

        return new AddressDTO(updatedAddress);
    }

    public void deleteAddress(Long id) {
        Address address = fetchAddressOrThrowErrorIfNotFound(id);
        address.removeFromUser();
        addressRepository.delete(address);
    }

}
