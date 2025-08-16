package com.crud.users.dtos.external;

public record ViaCepResponseDTO(
        String erro,
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String estado
) {

    public ViaCepResponseDTO(String erro) {
        this(erro, null, null, null, null, null);
    }

    public ViaCepResponseDTO(String cep, String logradouro, String bairro, String localidade, String estado) {
        this(null, cep, logradouro, bairro, localidade, estado);
    }
}
