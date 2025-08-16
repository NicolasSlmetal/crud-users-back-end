package com.crud.users.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crud.users.entities.Address;
import com.crud.users.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.crud.users.utils.MockFactory.getDefaultAddress;
import static com.crud.users.utils.MockFactory.getDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    private User insertDefaultDataReturningCreatedUser() {
        User user = getDefaultUser();

        return userRepository.save(user);
    }

    @Test
    @DisplayName("Should throw an error when an address with null fields pass to persist")
    public void shouldThrowAnErrorWhenAnAddressWithNullFieldsPassToPersist() {
        User savedUser = insertDefaultDataReturningCreatedUser();
        Address address = getDefaultAddress(savedUser);

        address.setCity(null);
        assertThrows(DataIntegrityViolationException.class, () -> addressRepository.save(address));
    }

    @Test
    @DisplayName("Should throw an error when an address with exceeding size fields pass to persist")
    public void shouldThrowAnErrorWhenAnAddressWithExceedingSizeFieldsPassToPersist() {
        User savedUser = insertDefaultDataReturningCreatedUser();
        Address address = getDefaultAddress(savedUser);

        address.setCep("0".repeat(9));
        assertThrows(DataIntegrityViolationException.class, () -> addressRepository.save(address));
    }

    @Test
    @DisplayName("Should throw an error when an address with no User pass to persist")
    public void shouldThrowAnErrorWhenAnAddressWithNoUserPassToPersist() {
        Address address = getDefaultAddress(null);

        assertThrows(DataIntegrityViolationException.class, () -> addressRepository.save(address));
    }

    @Test
    @DisplayName("Should include the address in user fetch when address is inserted before")
    public void shouldIncludeTheAddressInUserFetchWhenAddressIsInsertedBefore() {
        User savedUser = insertDefaultDataReturningCreatedUser();

        Address address = getDefaultAddress(savedUser);

        savedUser.setAddresses(new ArrayList<>(List.of(address)));
        Address savedAddress = addressRepository.save(address);

        Optional<User> fetchedUser = userRepository.findById(savedUser.getId());

        assertTrue(fetchedUser.isPresent());

        List<Address> userAddresses = fetchedUser.get().getAddresses();
        assertFalse(userAddresses.isEmpty());
        Address fetchedAddress = userAddresses.get(0);
        assertEquals(savedAddress.getId(), fetchedAddress.getId());
    }

    @Test
    @DisplayName("Should deny insert an address with an unsaved user")
    public void shouldDenyInsertAnAddressWithAnUnsavedUser() {
        Address address = getDefaultAddress(getDefaultUser());


        assertThrows(DataIntegrityViolationException.class, () -> addressRepository.save(address));
    }

    @Test
    @DisplayName("Should keep user even when an address is deleted")
    public void shouldKeepUserEvenWhenAnAddressIsDeleted() {
        User user = insertDefaultDataReturningCreatedUser();

        Address address = getDefaultAddress(user);
        user.setAddresses(new ArrayList<>(List.of(address)));
        Address savedAddress = addressRepository.save(getDefaultAddress(user));

        savedAddress.removeFromUser();
        addressRepository.deleteById(savedAddress.getId());

        Optional<User> userAfterAddressDelete = userRepository.findById(user.getId());

        assertTrue(userAfterAddressDelete.isPresent());
        List<Address> addressesAfterDelete = userAfterAddressDelete.get().getAddresses();
        assertTrue(addressesAfterDelete.isEmpty());
    }

}
