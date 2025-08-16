package com.crud.users.services;

import com.crud.users.dtos.address.AddressDTO;
import com.crud.users.dtos.external.ViaCepResponseDTO;
import com.crud.users.exceptions.InvalidCepException;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";
    private final ApiRequestService apiRequestService;

    public CepService(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public AddressDTO findAddressByCep(String cep) {
        String url = VIA_CEP_URL.replace("{cep}", cep);
        InvalidCepException invalidCepException = new InvalidCepException(cep);
        ViaCepResponseDTO response = apiRequestService.fetchAndMapForType(url, ViaCepResponseDTO.class, invalidCepException);

        String error = response.erro();
        if (error != null && error.equals("true")) {
            throw invalidCepException;
        }

        return new AddressDTO(response);
    }

    public void validateCep(String cep) {
        ViaCepResponseDTO response = fetchAddress(cep);

        String error = response.erro();
        if (error != null && error.equals("true")) {
            throw new InvalidCepException(cep);
        }
    }

    private ViaCepResponseDTO fetchAddress(String cep) {
        String url = VIA_CEP_URL.replace("{cep}", cep);
        InvalidCepException invalidCepException = new InvalidCepException(cep);
        return apiRequestService.fetchAndMapForType(url, ViaCepResponseDTO.class, invalidCepException);
    }

}
