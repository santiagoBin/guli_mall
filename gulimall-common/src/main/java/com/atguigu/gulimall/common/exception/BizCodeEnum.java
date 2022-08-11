package com.atguigu.gulimall.common.exception;

public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数校验失败");

    private int code;
    private String msg;

    BizCodeEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
