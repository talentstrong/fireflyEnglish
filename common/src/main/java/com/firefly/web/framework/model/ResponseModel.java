package com.firefly.web.framework.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firefly.service.framework.BasicCode;
import lombok.Data;

@Data
public class ResponseModel implements Serializable {

    private String respCode; // 返回码
    private String respMsg; // 返回描述


    @JsonIgnore
    public void setResult(BasicCode result) {
        this.respCode = result.getResultCode();
        this.respMsg = result.getResultMsg();
    }
}
