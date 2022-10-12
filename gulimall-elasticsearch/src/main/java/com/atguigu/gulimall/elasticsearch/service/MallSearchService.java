package com.atguigu.gulimall.elasticsearch.service;

import com.atguigu.gulimall.elasticsearch.entity.vo.SearchParam;
import com.atguigu.gulimall.elasticsearch.entity.vo.SearchResult;


public interface MallSearchService {
    SearchResult search(SearchParam searchParam);
}
