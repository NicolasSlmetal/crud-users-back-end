# Descrição

Aplicação Back-end de um CRUD de usuários desenvolvida utilizando Java 17, Spring Boot, H2 e Oracle XE 21. O projeto apresenta as seguintes funcionalidades:

- Endpoint para consultar todos os usuários: `GET http://localhost:8080/users`
- Endpoint para cadastrar um usuário: `POST http://localhost:8080/users`
- Endpoint para consultar um usuário: `GET http://localhost:8080/users/{id}`
- Endpoint para atualizar um usuário: `PUT http://localhost:8080/users/{id}`
- Endpoint para deletar um usuário: `DELETE http://localhost:8080/users/{id}`
- Endpoint para cadastrar um endereço: `POST http://localhost:8080/users/{user-id}/addresses`
- Endpoint para atualizar um endereço: `PUT http://localhost:8080/users/{user-id}/addresses/{address-id}`
- Endpoint para consultar um endereço específico do usuário: `GET http://localhost:8080/users/{user-id}/addresses/{address-id}`
- Endpoint para remover um endereço: `DELETE http://localhost:8080/users/{user-id}/addresses/{address-id}`
- Endpoint para consultar endereço com base no cep: `GET http://localhost:8080/cep/{cep}` 

# Requisitos

- Java 17;
- Maven;
- Banco de dados Oracle (para produção).

# Como executar em ambiente Dev

1. Clone o projeto com `git clone https://github.com/NicolasSlmetal/crud-users-back-end`
2. Execute o comando `mvn spring-boot:run`
3. Caso prefira, use uma IDE de preferência e procure o arquivo `UsersApplication.java` e execute-o. É o entrypoint da aplicação
4. A aplicação estará disponível em `http://localhost:8080`.

# Como executar em ambiente de produção
1. Clone o projeto com `git clone https://github.com/NicolasSlmetal/crud-users-back-end`
2. Defina as variáveis de ambiente `DB_HOST`, `DB_PORT`, `SID`, `DB_USER` e `DB_PASSWORD`
3. Execute o comando `mvn spring-boot:run -Dspring-boot.run.profiles=prod`
4. Caso prefira, use uma IDE de preferência e procure o arquivo `UsersApplication.java` e execute-o. É o entrypoint da aplicação
5. A aplicação estará disponível em `http://localhost:8080`.

# Instruções para os endpoints

## Listagem de usuários

Basta fazer uma requisição `GET` para o endereço `http://localhost:8080/users`;

## Listagem de usuário por Id

Faça uma requisição `GET` para o endpoint `http://localhost:8080/users/{id}` substituindo "{id}" pelo Id pretendido. Caso o usuário não exista, será retornada a seguinte resposta:

```json

{
    "message": "User not found"
}

```

## Cadastro de usuário

Deverá ser feito uma requisição `POST` para o endpoint `http://localhost:8080/users`, enviando também o seguinte body:
```json

{
    "name": "nome",
    "email": "email",
    "phone": "telefone-sem-formatação"
}


```
OBS: Todas as propriedades são obrigatórias.

## Edição de usuário

Deverá ser feito uma requisição `PUT` para o endpoint `http://localhost:8080/users/{id}`, substituindo o {id} pelo id pretendido e enviando também o seguinte body:
```json

{
    "name": "nome",
    "email": "email",
    "phone": "telefone-sem-formatação"
}
```
OBS: Todas as propriedades são opcionais. No entanto, ao serem fornecidas, serão validadas antes de enviar.

## Remoção de usuário

Deverá ser feito uma requisição `DELETE` para o endpoint `http://localhost:8080/users/{id}`, substituindo o {id} pelo id pretendido.

## Criação de endereço

Deverá ser enviado uma requisição `POST` para o endpoint `http://localhost:8080/users/{id}/addresses`, substituindo o {id} pelo id pretendido. Deverá ser enviado também o body:
```json

{
    "city": "Cidade",
    "neighborhood": "Bairro",
    "street": "Rua",
    "number": "Número",
    "state": "Estado",
    "cep": "CEP sem formatação"
}

```
OBS: Todas as propriedades são obrigatórias e o CEP será validado.

## Atualização de endereço

Deverá ser enviado uma requisição `PUT` para o endpoint `http://localhost:8080/users/{user-id}/addresses/{address-id}`, substituindo o {user-id} pelo id de usuário pretendido e {address-id} para o endereço pretendido. Deverá ser enviado também o body:
```json

{
    "city": "Cidade",
    "neighborhood": "Bairro",
    "street": "Rua",
    "number": "Número",
    "state": "Estado",
    "cep": "CEP sem formatação"
}

```
OBS: Todas as propriedades são opcionais, mas as propriedades serão validadas conforme forem fornecidas.

## Remoção de endereço

Deverá ser enviado uma requisição `DELETE` para o endpoint `http://localhost:8080/users/{user-id}/addresses/{address-id}`, substituindo o {user-id} pelo id de usuário pretendido e {address-id} para o endereço pretendido.

## Consulta de endereço pelo CEP

Devera ser enviado uma requisição `GET` para o endpoint `http://localhost:8080/cep/{cep}`, substituindo o {cep} pelo CEP sem formatação.

