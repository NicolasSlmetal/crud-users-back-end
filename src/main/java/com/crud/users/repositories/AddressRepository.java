package com.crud.users.repositories;

import java.util.List;
import com.crud.users.entities.Address;
import com.crud.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser (User user);
}
