package com.crud.users.entities;

import com.crud.users.dtos.user.CreateUserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    public User(CreateUserDTO createUserDTO) {
        setName(createUserDTO.name());
        setEmail(createUserDTO.email());
        setPhone(createUserDTO.phone());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.ALL,
            targetEntity = Address.class,
            fetch = FetchType.EAGER,
            mappedBy = "user"
    )
    private List<Address> addresses;

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
