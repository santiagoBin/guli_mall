package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.auth.feign.MemberFeignClient;
import com.atguigu.gulimall.common.constant.AuthServerConstant;
import com.atguigu.gulimall.common.exception.BizCodeEnum;
import com.atguigu.gulimall.common.to.UserLoginTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.atguigu.gulimall.common.constant.AuthServerConstant.LOGIN_USER;

@Controller
public class LoginController {
    @Autowired
    MemberFeignClient memberFeignClient;

    @GetMapping("login.html")
    public String loginPage(HttpSession session){
        Object attribute = session.getAttribute(LOGIN_USER);
        if (attribute==null){
            return "login";
        }else {
            return "redirect:http://gulimall.com";
        }
    }

    @PostMapping("login")
    @ResponseBody
    public R doLogin(@RequestBody @Validated UserLoginTo userLoginTo, RedirectAttributes attributes, HttpSession session, BindingResult result){
        Map<String, String> errors = new HashMap<>();
        if (result.getFieldErrors().size()>0){
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            R error = R.error(BizCodeEnum.LOGIN_EMPTY_EXCEPTION.getCode(), BizCodeEnum.LOGIN_EMPTY_EXCEPTION.getMsg());
            error.putAll(errors);
            return error;
        }
        R login = memberFeignClient.login(userLoginTo);
        if (login.getCode()==0){
            MemberRespVo memberRespVo = login.getData(new TypeReference<MemberRespVo>() {
            });
            session.setAttribute(LOGIN_USER,memberRespVo);
            R r = R.ok();
            r.put("loginUser", memberRespVo);
            return r;
        }else {
            return login;
        }
    }

    @GetMapping("loguot.html")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute(LOGIN_USER);
        request.getSession().invalidate();
        return "redirect:http://gulimall.com";
    }

}
