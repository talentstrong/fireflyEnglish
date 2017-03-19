package com.firefly.web.framework.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseJsonModel extends ResponseModel implements Serializable {
    private boolean isJsonp;
}
