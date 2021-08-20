package com.datastruct.datastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//spring will respond with this status code whenever we hit this exception
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EtAuthException extends RuntimeException{
    public EtAuthException(String message){
        super(message);
    }
}
