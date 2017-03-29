package com.firefly.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import org.springframework.util.StringUtils;

/**
 * 客户信息
 * 
 * @author Damon
 */
@Data
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = -9093723670825379325L;

    /**
     * 客户序号 db_column: id
     */
    private long id;

    /**
     * 昵称
     */
    private String nickName;
}
