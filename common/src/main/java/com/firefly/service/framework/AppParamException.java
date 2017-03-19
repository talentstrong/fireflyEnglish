package com.firefly.service.framework;

import com.firefly.service.framework.ServiceResultCode;

public class AppParamException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    //异常码
    private String code;
    //异常描述
    private String msg;
    
    public AppParamException(){
        super();
    }
    
    public AppParamException(String code,String msg){
        super();
        this.code=code;
        this.msg=msg;
    }
    
    public AppParamException(String code,String msg, Throwable cause){
        super(code+":"+msg,cause);
        this.code=code;
        this.msg=msg;
    }
    
    public AppParamException(ServiceResultCode serviceResultCode){
        super();
        this.code=serviceResultCode.getResultCode();
        this.msg=serviceResultCode.getResultMsg();
    }
    
    public AppParamException(ServiceResultCode serviceResultCode, Throwable cause){
        super(serviceResultCode.getResultCode()+":"+serviceResultCode.getResultMsg(),cause);
        this.code=serviceResultCode.getResultCode();
        this.msg=serviceResultCode.getResultMsg();
    }
    
    public  String getCode(){
        return code;
    }
    
    public String getMsg(){
        return msg;
    }
    
}
