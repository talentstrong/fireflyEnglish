package com.firefly.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CampApply {
    private long id;
    private long userId;
    private String mobile;
    private String weixinAccount;
    private String name;
    private String childAge;
    private String canSpeakEnglish;
    private Date createTime;
    private Date updateTime;
}
