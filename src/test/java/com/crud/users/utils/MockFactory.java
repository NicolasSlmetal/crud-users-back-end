package com.crud.users.utils;

import com.crud.users.dtos.external.ViaCepResponseDTO;
import com.crud.users.entities.Address;
import com.crud.users.entities.User;

public class MockFactory {

    public static User getDefaultUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@email.com");
        user.setPhone("1197766367");
        return user;
    }

    public static Address getDefaultAddress(User user) {
        Address address = new Address();
        address.setUser(user);
        address.setStreet("Street");
        address.setState("State");
        address.setCity("City");
        address.setCep("08887372");
        address.setNeighborhood("Neighborhood");
        address.setNumber("12");
        return address;
    }

    public static ViaCepResponseDTO getDefaultViaCepResponseDTO() {
        return new ViaCepResponseDTO("11111-111", "street", "neighborhood", "city", "state");
    }

    public static ViaCepResponseDTO getDefauCepResponseDTO(String cep) {
        cep = cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
        return new ViaCepResponseDTO(cep, "street", "neighborhood", "city", "state");
    }

    public static ViaCepResponseDTO getErrorViaCepResponseDTO() {
        return new ViaCepResponseDTO("true");
    }
}
