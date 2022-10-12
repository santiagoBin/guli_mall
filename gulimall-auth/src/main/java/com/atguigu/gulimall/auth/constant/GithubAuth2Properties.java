package com.atguigu.gulimall.auth.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class GithubAuth2Properties {

    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.clientSecret}")
    private String clientSecret;
    @Value("${github.authorizeUrl}")
    private String authorizeUrl;
    @Value("${github.redirectUrl}")
    private String redirectUrl;
    @Value("${github.accessTokenUrl}")
    private String accessTokenUrl;
    @Value("${github.userInfoUrl}")
    private String userInfoUrl;



}
