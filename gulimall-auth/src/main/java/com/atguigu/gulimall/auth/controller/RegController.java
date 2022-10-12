package com.atguigu.gulimall.auth.controller;

import com.atguigu.gulimall.auth.feign.MemberFeignClient;
import com.atguigu.gulimall.auth.feign.ThirdPartyFeignClient;
import com.atguigu.gulimall.common.exception.BizCodeEnum;
import com.atguigu.gulimall.common.to.UserRegisterTo;
import com.atguigu.gulimall.common.utils.Constant;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.valid.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RegController {
    @Autowired
    ThirdPartyFeignClient thirdPartyFeignClient;
    @Autowired
    MemberFeignClient memberFeignClient;
    @Autowired
    StringRedisTemplate redisTemplate;
    @GetMapping("register.html")
    public String RegPage(){
        return "reg";
    }

    @GetMapping("sms/sendCode")
    @ResponseBody
    public R sendCode(@RequestParam String phone){
        return thirdPartyFeignClient.sendCode(phone);
    }

    @PostMapping(value = "register")
    @ResponseBody
    public R doRegister(@RequestBody @Validated(AddGroup.class) UserRegisterTo user, BindingResult result, RedirectAttributes attributes){
        Map<String, String> errors = new HashMap<>();
        if (result.getFieldErrors().size()>0){
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            R error = R.error(BizCodeEnum.REGISTER_EXCEPTION.getCode(), BizCodeEnum.REGISTER_EXCEPTION.getMsg());
            error.putAll(errors);
            return error;
        }
        String pre_code = redisTemplate.opsForValue().get(Constant.REGISTER_CODE_PREFIX_KEY + user.getPhone());
        String code = "";
        if(!StringUtils.isEmpty(pre_code)){
            code = pre_code.substring(0,pre_code.indexOf("_"));
        }
        if (user.getCode().equals(code)){
            R register = memberFeignClient.register(user);
            if (register.getCode() == 0){
                return R.ok();
            }else {
                R error = R.error(BizCodeEnum.REGISTER_EXCEPTION.getCode(), BizCodeEnum.REGISTER_EXCEPTION.getMsg());
                error.put("phone",(String) register.get("phone"));
                error.put("userName",(String) register.get("userName"));
                return error;

            }
        }else {
            R error = R.error(BizCodeEnum.REGISTER_EXCEPTION.getCode(), BizCodeEnum.REGISTER_EXCEPTION.getMsg());
            error.put("error_code","验证码错误，请重试。");
            return error;
        }
    }

}
