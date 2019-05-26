package com.midas2018.root.model;

import org.apache.commons.lang3.StringUtils;

import com.midas2018.root.model.supoort.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultContainer<T> {
    private ExceptionCode code = ExceptionCode.SUCCESS;
    private String message;
    private T data;

    public ResultContainer(T data) {
        this.data = data;
    }

    public int getCode() {
        return code.getCode();
    }

    public void setCode(ExceptionCode code) { this.code = code; }
    public String getMessage() {
        return StringUtils.isBlank(message) ? code.getMessage() : message;
    }

    public void setMessage(String message) { this.message = message; }
}

