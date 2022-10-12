package com.atguigu.gulimall.elasticsearch.controller;

import com.atguigu.gulimall.elasticsearch.entity.vo.SearchParam;
import com.atguigu.gulimall.elasticsearch.service.MallSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.atguigu.gulimall.elasticsearch.entity.vo.SearchResult;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;
    @GetMapping("/list.html")
    public String list(SearchParam searchParam, Model model, HttpServletRequest request){
        searchParam.set_queryString(request.getQueryString());
        SearchResult result = mallSearchService.search(searchParam);
        model.addAttribute("result",result);
        return "list";
    }
}
