package com.atguigu.gulimall.common.config;


import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.common.exception.BizCodeEnum;
import com.atguigu.gulimall.common.utils.R;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 秒杀限流异常处理程序
 *
 * @author Miozus
 * @date 2022/03/30
 */
@Configuration
public class SeckillBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getMsg());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(error));
    }
}

