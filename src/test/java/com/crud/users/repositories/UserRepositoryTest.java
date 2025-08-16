package com.crud.users.repositories;

import static com.crud.users.utils.MockFactory.getDefaultAddress;
import static com.crud.users.utils.MockFactory.getDefaultUser;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crud.users.entities.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crud.users.entities.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should throw an error when an user with null required fields pass to persist")
    public void shouldThrowAnErrorWhenAnUserWithNullRequiredFieldsPassToPersist() {
        User user = getDefaultUser();
        user.setEmail(null);

        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    @Test
    @DisplayName("Should throw an error when an user with exceeding fields pass to persist")
    public void shouldThrowAnErrorWhenAnUserWithExceedingFieldsPassToPersist() {
        User user = getDefaultUser();
        user.setName("A".repeat(256));

        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    @Test
    @DisplayName("Should insert user and his addresses")
    public void shouldInsertUserAndHisAddresses() {
        User user = getDefaultUser();
        List<Address> addresses = new ArrayList<>(List.of(getDefaultAddress(user)));
        user.setAddresses(addresses);

        User insertedUser = userRepository.save(user);

        assertNotNull(insertedUser.getId());
        assertFalse(insertedUser.getAddresses().stream().anyMatch(address -> address.getId() == null));
        assertEquals(addresses, insertedUser.getAddresses());
    }

    @Test
    @DisplayName("Should update the user and his address")
    public void shouldUpdateTheUserAndHisAddress() {
        User user  = getDefaultUser();
        List<Address> addresses = new ArrayList<>(List.of(getDefaultAddress(user)));
        user.setAddresses(addresses);

        User insertedUser = userRepository.save(user);
        List<Address> modifiedAddresses = new ArrayList<>(insertedUser.getAddresses());
        modifiedAddresses.forEach(a -> a.setStreet("New Street"));
        String newName = "Johnson Doe";
        insertedUser.setName(newName);

        User modifiedUser = userRepository.save(insertedUser);

        assertEquals(newName, modifiedUser.getName());
        assertEquals(modifiedAddresses, modifiedUser.getAddresses());
    }

    @Test
    @DisplayName("Should delete user and his addresses")
    public void shouldDeleteUserAndHisAddresses() {
        User user = getDefaultUser();
        List<Address> addresses = new ArrayList<>(List.of(getDefaultAddress(user)));
        user.setAddresses(addresses);

        User insertedUser = userRepository.save(user);
        Long userId = insertedUser.getId();
        List<Long> addressesId = insertedUser.getAddresses().stream().map(Address::getId).toList();

        userRepository.deleteById(userId);

        Optional<User> optionalUser = userRepository.findById(userId);
        List<Address> addressesOfUser = addressRepository.findAllById(addressesId);

        assertTrue(optionalUser.isEmpty());
        assertTrue(addressesOfUser.isEmpty());
    }
}
