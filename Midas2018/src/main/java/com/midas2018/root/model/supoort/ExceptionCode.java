package com.midas2018.root.model.supoort;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    SUCCESS(HttpStatus.OK),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final int code;
    private final String message;

    ExceptionCode(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean equals(ExceptionCode other) {
        if (this == other) {
            return true;
        }
        return code == other.getCode();
    }

    public boolean equals(int code) {
        return this.code == code;
    }
}
