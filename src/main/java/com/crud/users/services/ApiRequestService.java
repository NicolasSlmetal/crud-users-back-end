package com.crud.users.services;


import com.crud.users.exceptions.RequestServerException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ApiRequestService {

    private final RestTemplate restTemplate;

    public ApiRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T fetchAndMapForType(String url, Class<T> responseBodyType, RuntimeException exceptionWhenClientError) {
        RequestEntity<T> request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        try {
            return restTemplate.exchange(request, responseBodyType).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            HttpStatusCode statusCode = exception.getStatusCode();
            if (statusCode.is4xxClientError()) {
                throw exceptionWhenClientError;
            }
            throw new RequestServerException(exception.getMessage(), exception.getResponseBodyAsString(), url);
        }
    }
}
