package com.crud.users.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.crud.users.utils.MockFactory.getDefaultViaCepResponseDTO;
import static com.crud.users.utils.MockFactory.getErrorViaCepResponseDTO;
import static org.mockito.MockitoAnnotations.openMocks;

import com.crud.users.exceptions.RequestServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.crud.users.dtos.external.ViaCepResponseDTO;
import com.crud.users.exceptions.InvalidCepException;

public class CepServiceTest {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";
    
    @Mock
    private ApiRequestService apiRequestService;
    
    @InjectMocks
    private CepService cepService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }


    @Test
    @DisplayName("Should accept a cep which ViaCep identifies the address")
    public void shouldAcceptACepWhichViaCepIdentifiesTheAddress() {
        String cep = "1111111";
        String requestURL = VIA_CEP_URL.replace("{cep}", cep);
        when(apiRequestService
        .fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class)))
        .thenReturn(getDefaultViaCepResponseDTO());

        assertDoesNotThrow(() -> cepService.validateCep(cep));
        verify(apiRequestService, times(1)).fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class));
    }

    @Test
    @DisplayName("Should throw an error if ViaCep returns a response with defined error")
    public void shouldThrowAnErrorIfViaCepReturnsAResponseWithDefinedError() {
        String cep = "1111111";
        String requestURL = VIA_CEP_URL.replace("{cep}", cep);
        when(apiRequestService
        .fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class)))
        .thenReturn(getErrorViaCepResponseDTO());

        assertThrows(InvalidCepException.class,() -> cepService.validateCep(cep));
        verify(apiRequestService, times(1)).fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class));
    }

    @Test
    @DisplayName("Should throw the passed exception if the API service gets a bad request response") 
    public void shouldThrowThePassedExceptionIfTheAPIServiceGetsABadRequestResponse() {
        String cep = "1111111";
        String requestURL = VIA_CEP_URL.replace("{cep}", cep);
        
        
        when(apiRequestService
        .fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class)))
        .thenThrow(InvalidCepException.class);

        assertThrows(InvalidCepException.class,() -> cepService.validateCep(cep));
        verify(apiRequestService, times(1)).fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class));
    }

    @Test
    @DisplayName("Should throw a request server error when API service gets a server error")
    public void shouldThrowARequestServerErrorWhenAPIServiceGetsAServerError() {
        String cep = "111111";
        String requestURL = VIA_CEP_URL.replace("{cep}", cep);

        when(apiRequestService
                .fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class))
        ).thenThrow(RequestServerException.class);

        assertThrows(RequestServerException.class,() -> cepService.validateCep(cep));
        verify(apiRequestService, times(1)).fetchAndMapForType(eq(requestURL), eq(ViaCepResponseDTO.class), any(InvalidCepException.class));
    }
}
