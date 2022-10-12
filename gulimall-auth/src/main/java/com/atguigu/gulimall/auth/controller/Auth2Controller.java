package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.auth.constant.GithubAuth2Properties;
import com.atguigu.gulimall.auth.feign.MemberFeignClient;
import com.atguigu.gulimall.common.utils.HttpUtils;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.atguigu.gulimall.common.constant.AuthServerConstant.LOGIN_USER;

@Slf4j
@Controller
public class Auth2Controller {
    @Autowired
    GithubAuth2Properties githubAuth2Properties;
    @Autowired
    MemberFeignClient memberFeignClient;

    @GetMapping("oauth2/authorize")
    public String goGithubAuth2Page(){
        String url = githubAuth2Properties.getAuthorizeUrl()+
                "?client_id=" + githubAuth2Properties.getClientId() +
                "&redirect_uri=" + githubAuth2Properties.getRedirectUrl();
        log.info("授权url:{}", url);
        return "redirect:" + url;
    }

    @GetMapping("github/oauth2/callback")
    public String callback(@RequestParam("code") String code, HttpSession session) throws Exception {
        log.info("code={}", code);
        // code换token
        String accessToken = getAccessToken(code);
        // token换userInfo
        String userInfo = getUserInfo(accessToken);
        JSONObject jsonObject = JSONObject.parseObject(userInfo);
        MemberRespVo memberRespVo = new MemberRespVo();
        memberRespVo.setId(jsonObject.getString("id"));
        memberRespVo.setNickname(jsonObject.getString("login"));
        memberRespVo.setUsername(jsonObject.getString("login"));
        R r = memberFeignClient.gitLogin(memberRespVo);
        MemberRespVo member = r.getData(new TypeReference<MemberRespVo>() {
        });
        if (r.getCode()==0){
            session.setAttribute(LOGIN_USER,member);
            return "redirect:http://gulimall.com";
        }else {
            return "redirect:http://auth.gulimall.com/login.html";
        }

    }

    private String getUserInfo(String accessToken) throws Exception {
        Map<String, String> headers = new HashMap<>();
        Map<String,String> querys = new HashMap<>();
        headers.put("accept","application/json");
        headers.put("Authorization","token " + accessToken);
        HttpResponse httpResponse = HttpUtils.doGet("https://api.github.com/", "user", "get", headers, querys);
        String response = EntityUtils.toString(httpResponse.getEntity());
        return response;
    }

    private String getAccessToken(String code) throws Exception {
        Map<String, String> headers = new HashMap<>();
        Map<String,String> querys = new HashMap<>();
        querys.put("client_id",githubAuth2Properties.getClientId());
        querys.put("client_secret",githubAuth2Properties.getClientSecret());
        querys.put("code",code);
        querys.put("grant_type","authorization_code");
        headers.put("accept","application/json");
        HttpResponse httpResponse = HttpUtils.doGet("https://github.com/", "login/oauth/access_token", "get", headers, querys);
        org.apache.http.HttpEntity entity = httpResponse.getEntity();
        String response = EntityUtils.toString(httpResponse.getEntity());
        log.info("entity={}", entity);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String accessToken = jsonObject.getString("access_token");
        log.info("accessToken={}", accessToken);
        return accessToken;

    }
}
