package com.midas2018.root.exceptions;

import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.model.supoort.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ResultContainer<Object>> handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest req) {
        ResultContainer<Object> rc = new ResultContainer<>(null);
        rc.setCode(ExceptionCode.INTERNAL_SERVER_ERROR);
        rc.setMessage(e.getMessage());
        return new ResponseEntity<>(rc, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
