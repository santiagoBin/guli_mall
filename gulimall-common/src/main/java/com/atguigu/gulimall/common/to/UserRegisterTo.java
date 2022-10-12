package com.atguigu.gulimall.common.to;

import com.atguigu.gulimall.common.valid.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterTo {
    @NotEmpty(message = "用户名不得为空！",groups = {AddGroup.class})
    private String userName;
    @NotEmpty(message = "密码不得为空！" ,groups = {AddGroup.class})
    private String password;
    @NotEmpty(message = "手机号不得为空！",groups = {AddGroup.class})
    private String phone;
    @NotEmpty(message = "验证码不得为空！",groups = {AddGroup.class})
    private String code;
}
