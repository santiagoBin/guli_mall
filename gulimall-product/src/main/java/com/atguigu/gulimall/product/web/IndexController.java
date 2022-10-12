package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.entity.vo.Catelog2Vo;
import com.atguigu.gulimall.product.service.CategoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

import static com.atguigu.gulimall.common.constant.AuthServerConstant.LOGIN_USER;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping({"/","/index.html"})
    public String indexPage(Model model,HttpSession session, @ModelAttribute(LOGIN_USER) String memberRespVo){
        List<CategoryEntity> categoryEntityList = categoryService.getLevel1Categorys();
        if (!StringUtils.isEmpty(memberRespVo)){
            Gson gson = new Gson();
            MemberRespVo memberRespVo1 = gson.fromJson(memberRespVo, MemberRespVo.class);
            session.setAttribute(LOGIN_USER,memberRespVo1);
        }
        model.addAttribute("categories",categoryEntityList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/json/catelog.json")
    public Map<String,List<Catelog2Vo>> getCatelogJson(){
        Map<String,List<Catelog2Vo>> map = categoryService.getIndexJsonData();
        return map;
    }


}
