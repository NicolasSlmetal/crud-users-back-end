package com.crud.users.exceptions;

import lombok.Getter;

@Getter
public class RequestServerException extends RuntimeException {

    private final Object bodyResponse;
    private final String url;

    public RequestServerException(String message, Object bodyResponse, String url) {
        super(message);
        this.bodyResponse = bodyResponse;
        this.url = url;
    }
}
