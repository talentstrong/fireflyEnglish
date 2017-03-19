package com.firefly.web.framework.model;

import java.io.Serializable;

import com.firefly.dto.CustomerDto;

import lombok.Data;

@Data
public class ResponseModel implements Serializable {

    /**
     * 客户信息
     */
    private CustomerDto customer;
}
