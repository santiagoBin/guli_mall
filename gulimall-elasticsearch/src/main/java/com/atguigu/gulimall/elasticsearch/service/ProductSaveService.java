package com.atguigu.gulimall.elasticsearch.service;

import com.atguigu.gulimall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {
    boolean saveEsProduct(List<SkuEsModel> skuEsModels) throws IOException;
}
