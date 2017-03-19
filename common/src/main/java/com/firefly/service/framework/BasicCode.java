package com.firefly.service.framework;

public enum BasicCode implements ServiceResultCode {

    SUCCESS("00000000", "success"),

    FAILED("99999999", "failed"),

    ACCEPTED("11111111", "accepted"),

    INTERNAL_EXCEPTION("99999991", "internal exception"),

    EXTERNAL_EXCEPTION("99999992", "external exception"),

    PARAM_EXCEPTION("99999993", "param exception");

    private final String resultCode;

    private final String resultMsg;

    BasicCode(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public Boolean equals(ServiceResultCode respCode) {
        return this.resultCode.equals(respCode.getResultCode());
    }
}
