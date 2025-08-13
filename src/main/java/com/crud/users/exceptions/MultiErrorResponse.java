package com.crud.users.exceptions;

import java.util.List;

public record MultiErrorResponse(
        List<String> messages
) {
}
