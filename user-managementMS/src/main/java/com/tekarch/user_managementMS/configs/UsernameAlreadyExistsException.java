package com.tekarch.user_managementMS.configs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameAlreadyExistsException extends RuntimeException{

    public  UsernameAlreadyExistsException(String username){
        super("username already exist: " + username);
    }
}
