package com.crud.users.controllers;

import com.crud.users.dtos.address.AddressDTO;
import com.crud.users.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class CepController {

    private final AddressService addressService;


    public CepController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<AddressDTO> findAddressByCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(addressService.findAddressByCep(cep));
    }
}
