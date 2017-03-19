package com.firefly.web.framework.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseStreamModel<T> extends ResponseModel {
    private String contentType;
    private Map<String, String> headers;
    private T dataStream;
}
