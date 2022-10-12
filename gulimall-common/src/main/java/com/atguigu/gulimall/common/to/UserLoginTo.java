package com.atguigu.gulimall.common.to;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginTo {
    @NotEmpty(message = "登录账号不能为空！")
    private String loginAccount;
    @NotEmpty(message = "密码不能为空！")
    private String password;
}
