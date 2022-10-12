package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.auth.feign.MemberFeignClient;
import com.atguigu.gulimall.auth.utils.ConstantPropertiesUtil;
import com.atguigu.gulimall.auth.utils.HttpClientUtil;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.atguigu.gulimall.common.constant.AuthServerConstant.LOGIN_USER;

@Controller
public class WXController {
    @Autowired
    MemberFeignClient memberFeignClient;
    @GetMapping("wx/login")
    public String genQrConnect(HttpSession session){
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }
        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        //重定向到请求微信地址里面
        return "redirect:"+url;
    }


    @GetMapping("api/ucenter/wx/callback")
    public String callback(String code, String state, HttpSession session,RedirectAttributes attributes){
        //得到授权临时票据code
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +"?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        String result = null;
        try {
            result = HttpClientUtil.get(accessTokenUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(result, HashMap.class);
        String accessToken = (String)hashMap.get("access_token");
        String openid =(String) hashMap.get("openid");
            //访问微信的资源服务器，获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
        String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
        String resultUserInfo = null;
        try {
            resultUserInfo = HttpClientUtil.get(userInfoUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        HashMap<String,Object> userInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
        String nickname = (String)userInfoMap.get("nickname");
        MemberRespVo memberRespVo = new MemberRespVo();
        memberRespVo.setId(openid);
        memberRespVo.setNickname(nickname);
        memberRespVo.setUsername(nickname);
        R r = memberFeignClient.wxLogin(memberRespVo);
        MemberRespVo data = r.getData(new TypeReference<MemberRespVo>() {
        });
        if (r.getCode()==0){
            String s = gson.toJson(data);
            attributes.addAttribute("loginUser",s);
            return "redirect:http://gulimall.com";
        }else {
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
