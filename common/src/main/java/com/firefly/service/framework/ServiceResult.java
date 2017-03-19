package com.firefly.service.framework;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ServiceResult<T> implements ServiceResultCode, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private T data;

    public ServiceResult() {
        super();
    }

    public ServiceResult(ServiceResultCode code) {
        this.code = code.getResultCode();
        this.message = code.getResultMsg();
    }

    public ServiceResult(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ServiceResult(String code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public String getResultCode() {
        return code;
    }

    public String getResultMsg() {
        return message;
    }
}
