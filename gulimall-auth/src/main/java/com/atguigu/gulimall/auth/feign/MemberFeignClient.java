package com.atguigu.gulimall.auth.feign;

import com.atguigu.gulimall.common.to.UserLoginTo;
import com.atguigu.gulimall.common.to.UserRegisterTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-member")

public interface MemberFeignClient {

    @PostMapping("/member/member/register")
    public R register(@RequestBody UserRegisterTo userRegisterTo);
    @PostMapping("/member/member/login")
    public R login(@RequestBody UserLoginTo userLoginTo);
    @PostMapping("/member/member/github/oauth2/login")
    public R gitLogin(@RequestBody MemberRespVo memberRespVo);
    @PostMapping("/member/member/wx/login")
    public R wxLogin(@RequestBody MemberRespVo memberRespVo);
}
