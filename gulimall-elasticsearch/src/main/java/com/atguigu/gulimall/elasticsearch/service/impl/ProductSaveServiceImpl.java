package com.atguigu.gulimall.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.elasticsearch.config.GulimallElasticSearchConfig;
import com.atguigu.gulimall.elasticsearch.constant.EsConstant;
import com.atguigu.gulimall.elasticsearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient client;

    @Override
    public boolean saveEsProduct(List<SkuEsModel> skuEsModels) throws IOException {
        log.info(skuEsModels.toString());
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel: skuEsModels
             ) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String s = JSON.toJSONString(skuEsModel);
            indexRequest.source(s, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }
        //TODO 1、如果批量错误
        BulkResponse bulk = client.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架成功:{}",collect);
        return b;
    }
}
