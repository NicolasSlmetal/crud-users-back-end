package com.crud.users.exceptions;

import lombok.Getter;

@Getter
public class RequestServerException extends RuntimeException {

    private Object bodyResponse;
    private String url;

    public RequestServerException(String message) {
        super(message);
    }

    public RequestServerException(String message, Object bodyResponse, String url) {
        super(message);
        this.bodyResponse = bodyResponse;
    }
}
