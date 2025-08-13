package com.crud.users.entities;


import com.crud.users.dtos.address.CreateAddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    public Address(CreateAddressDTO addressDTO) {
        setCep(addressDTO.cep());
        setState(addressDTO.state());
        setCity(addressDTO.city());
        setStreet(addressDTO.street());
        setNumber(addressDTO.number());
        setNeighborhood(addressDTO.neighborhood());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cep;

    @Column
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column
    private String neighborhood;

    @Column
    private String city;

    @Column(name = "address_state")
    private String state;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void removeFromUser() {
        if (user != null) {
            user.removeAddress(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCep(), address.getCep()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getNumber(), address.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCep(), getStreet(), getNumber());
    }
}
