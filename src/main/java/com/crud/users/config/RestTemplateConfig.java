package com.crud.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MimeType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;

@Configuration
public class RestTemplateConfig {


    @Bean
    public RestTemplate restTemplate(ResponseErrorHandler errorHandler) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }

    @Bean
    public ResponseErrorHandler responseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatusCode statusCode = response.getStatusCode();
                return statusCode.is4xxClientError() || statusCode.is5xxServerError();
            }

            @Override
            public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
                InputStream bodyStream = response.getBody();
                Optional<MediaType> contentType = Optional.ofNullable(response.getHeaders().getContentType());
                Charset responseCharset = contentType.map(MimeType::getCharset).orElse(Charset.defaultCharset());
                if (response.getStatusCode().is4xxClientError()) {
                    throw new HttpClientErrorException(response.getStatusCode(),
                            response.getStatusText(),
                            bodyStream.readAllBytes(),
                            responseCharset);
                }

                throw new HttpServerErrorException(response.getStatusCode(),
                        response.getStatusText(),
                        bodyStream.readAllBytes(),
                        responseCharset);
            }
        };
    }
}
