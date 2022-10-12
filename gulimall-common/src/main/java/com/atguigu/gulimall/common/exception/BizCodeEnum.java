package com.atguigu.gulimall.common.exception;

public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数校验失败"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常"),
    REGISTER_EXCEPTION(12000,"用户注册异常"),
    LOGIN_ERROR_PASSWD_EXCEPTION(12001,"用户登录密码错误异常"),
    LOGIN_ACCOUNT_NOT_FOUND_EXCEPTION(12002,"用户登录账户不存在异常"),
    LOGIN_EMPTY_EXCEPTION(12003,"用户登录账号或密码缺失异常"),
    NO_STOCK_EXCEPTION(21000,"商品库存不足");

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
