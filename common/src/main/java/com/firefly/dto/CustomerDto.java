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
    private Long id;
    /**
     * 是否自动登录
     */
    private Integer isautologin;
    /**
     * 性别
     */
    private String gender;
    /**
     * 地域
     */
    private String region;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 注册来源
     */
    private String route;
    /**
     * 客户姓名 db_column: name
     */
    private String name;
    /**
     * 客户电话 db_column: mobile
     */
    private String mobile;

    /**
     * 客户等级 db_column: level
     */
    private Integer level;
    /**
     * 最后更新时间 db_column: updatedatetime
     */
    private Date updatedatetime;
    /**
     * 身份证 db_column: idcard
     */
    private String idcard;
    /**
     * 1=启用 0=停用 2=未激活（借款申请用） 3=锁定 255=删除 db_column: status
     */
    private Integer status;
    /**
     * 邮箱地址 db_column: email
     */
    private String email;
    /**
     * qq db_column: qq
     */
    private String qq;
    /**
     * 居住地址 db_column: adress
     */
    private String adress;
    /**
     * description db_column: description
     */
    private String description;
    /**
     * 最后更新用户ID db_column: modifyuserid
     */
    private Long modifyuserid;
    /**
     * 创建时间 db_column: createdatetime
     */
    private Date createdatetime;
    /**
     * 最后登录时间 db_column: lastlogindatetime
     */
    private Date lastlogindatetime;

    /**
     * 是否机器人
     */
    private Integer isrobot;

    /**
     * 是否在线
     */
    private Integer isonline;

    /**
     * 汇付天下用户名
     */
    private String chinapnrid;

    /**
     * 邀请码
     */
    private String invitecode;

    /**
     * 特权本金
     */
    private BigDecimal earningTotalQuote;

    /**
     * 特权收益率
     */
    private BigDecimal earningTotalRate;

    /**
     * 汇付客户号
     */
    private String chinapnrusrid;

    /**
     * 投资总次数
     */
    private Integer investcounttotal;

    /**
     * 真实姓名绑定
     */
    private Integer realnameapprove;

    /**
     * 邀请人id
     */
    private Long inviterid;

    /**
     * 请求客户端ip
     */
    private String requestip;

    /**
     * 请求平台
     */
    private Integer platform;

    /**
     * 用户客户端
     */
    private String useragent;

    /**
     * 第一次登陆是否更改过密码
     */
    private Integer firstchangepwd;

    /**
     * 连连绑定的银行卡
     */
    private String lianlianCard;

    /**
     * CPS渠道编号
     */
    private String cocode;

    /**
     * 是否开通即信存管账户
     */
    private int isOpenJixin;

    /**
     * 页面隐藏重要信息处理后的手机号码
     */
    private String screenmobile;

    private String screenidcard;

    private String screenchinaprnid;

    public String getScreenchinaprnid() {
        if (!StringUtils.isEmpty(chinapnrid))
            return chinapnrid.substring(0, 8) + "****"
                    + chinapnrid.substring(chinapnrid.length() - 3, chinapnrid.length());
        else
            return chinapnrid;
    }

    public void setScreenchinaprnid(String screenchinaprnid) {
        if (!StringUtils.isEmpty(chinapnrid)) {
            this.screenchinaprnid = chinapnrid.substring(0, 8) + "****"
                    + mobile.substring(mobile.length() - 3, mobile.length());
        }
    }

    public String getScreenidcard() {
        if (!StringUtils.isEmpty(idcard))
            return idcard.substring(0, 4) + "**********" + idcard.substring(idcard.length() - 4, idcard.length());
        else
            return idcard;
    }

    public void setScreenidcard(String screenidcard) {
        if (!StringUtils.isEmpty(idcard))
            this.screenidcard = idcard.substring(0, 4) + "**********"
                    + idcard.substring(idcard.length() - 4, idcard.length());

    }

    public String getScreenmobile() {
        if (!StringUtils.isEmpty(mobile)) {
            return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length());
        } else {
            return screenmobile;
        }
    }

    public void setScreenmobile(String screenmobile) {
        if (!StringUtils.isEmpty(mobile)) {
            this.screenmobile = mobile.substring(0, 3) + "****"
                    + mobile.substring(mobile.length() - 4, mobile.length());
        }
    }

}
