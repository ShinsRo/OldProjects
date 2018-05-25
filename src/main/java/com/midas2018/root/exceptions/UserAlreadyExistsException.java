package com.midas2018.root.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserAlreadyExistsException extends Throwable {

    public UserAlreadyExistsException() {
        super("이미 존재하는 이메일입니다.");
    }
}
