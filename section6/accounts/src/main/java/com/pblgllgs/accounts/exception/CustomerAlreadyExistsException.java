package com.pblgllgs.accounts.exception;
/*
 *
 * @author pblgl
 * Created on 19-02-2025
 *
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException  extends RuntimeException{

    public CustomerAlreadyExistsException(String message){
        super(message);
    }
}
