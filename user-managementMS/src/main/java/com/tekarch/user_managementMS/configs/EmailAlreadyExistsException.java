package com.tekarch.user_managementMS.configs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailAlreadyExistsException extends RuntimeException{

    public  EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }

}