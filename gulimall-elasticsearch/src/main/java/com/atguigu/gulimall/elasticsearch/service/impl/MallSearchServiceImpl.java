package com.atguigu.gulimall.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.elasticsearch.config.GulimallElasticSearchConfig;
import com.atguigu.gulimall.elasticsearch.constant.EsConstant;
import com.atguigu.gulimall.elasticsearch.entity.vo.AttrResponseVo;
import com.atguigu.gulimall.elasticsearch.entity.vo.SearchParam;
import com.atguigu.gulimall.elasticsearch.entity.vo.SearchResult;
import com.atguigu.gulimall.elasticsearch.feign.ProductFeignClient;
import com.atguigu.gulimall.elasticsearch.service.MallSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MallSearchServiceImpl implements MallSearchService {
    @Autowired
    private RestHighLevelClient esHighLevelClient;
    @Autowired
    ProductFeignClient productFeignClient;
    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResult result = null;
        SearchRequest searchRequest = getSearchRequest(searchParam);
        try {
            SearchResponse searchResponse = esHighLevelClient.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
            result = getSearchResult(searchResponse, searchParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private SearchRequest getSearchRequest(SearchParam searchParam){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle",searchParam.getKeyword().toLowerCase(Locale.ROOT)));
        }
        if (null!=searchParam.getCatelog3Id()){
            boolQueryBuilder.filter(QueryBuilders.termQuery("catelogId",searchParam.getCatelog3Id()));
        }
        if (searchParam.getBrandId()!=null&&searchParam.getBrandId().size()>0){
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId",searchParam.getBrandId()));
        }
        if (searchParam.getAttrs()!=null&&searchParam.getAttrs().size()>0){
            searchParam.getAttrs().forEach(item->{
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                String[] s = item.split("_");
                String attrId = s[0];
                String[] attrValue = s[1].split(":");
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId",Long.parseLong(attrId)));
                boolQuery.must(QueryBuilders.termsQuery("attrs.attrValue",attrValue));
                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            });
        }
        //1.2.4 hasStock
        if(null != searchParam.getHasStock()){
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock",searchParam.getHasStock() == 1));
        }

        if (!StringUtils.isEmpty(searchParam.getSkuPrice())){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("sku_price");
            String[] s = searchParam.getSkuPrice().split("_");
            if (s.length == 2){
                rangeQueryBuilder.gte(s[0]).lte(s[1]);
            }else if(s.length ==1){
                if (searchParam.getSkuPrice().startsWith("_")){
                    rangeQueryBuilder.lte(s[1]);
                }
                if (searchParam.getSkuPrice().endsWith("_")){
                    rangeQueryBuilder.gte(s[0]);
                }
            }
            boolQueryBuilder.filter(rangeQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);

        if (!StringUtils.isEmpty(searchParam.getSort())){
            String[] s = searchParam.getSort().split("_");
            SortOrder sortOrder = "asc".equalsIgnoreCase(s[1]) ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(s[0], sortOrder);
        }

        //分页
        searchSourceBuilder.from((searchParam.getPageNum()-1)*EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }

//        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
//        brand_agg.field("brandId").size(50);
//
//        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
//        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
//        searchSourceBuilder.aggregation(brand_agg);
//
//        TermsAggregationBuilder catelog_agg = AggregationBuilders.terms("catelog_agg");
//        catelog_agg.field("catelogId").size(20);
//        catelog_agg.subAggregation(AggregationBuilders.terms("catelog_name_agg").field("catelogName").size(1));
//        searchSourceBuilder.aggregation(catelog_agg);
//
//        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
//        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrId");
//        attr_agg.subAggregation(attr_id_agg);
//        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrName").size(1));
//        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrValue").size(50));
//        searchSourceBuilder.aggregation(attr_agg);
        /**
         * 聚合分析
         */
        //1. 按照品牌进行聚合
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);


        //1.1 品牌的子聚合-品牌名聚合
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg")
                .field("brandName").size(1));
        //1.2 品牌的子聚合-品牌图片聚合
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg")
                .field("brandImg").size(1));

        searchSourceBuilder.aggregation(brand_agg);

        //2. 按照分类信息进行聚合
        TermsAggregationBuilder catelog_agg = AggregationBuilders.terms("catelog_agg");
        catelog_agg.field("catelogId").size(20);

        catelog_agg.subAggregation(AggregationBuilders.terms("catelog_name_agg").field("catelogName").size(1));

        searchSourceBuilder.aggregation(catelog_agg);

        //2. 按照属性信息进行聚合
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        //2.1 按照属性ID进行聚合
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        attr_agg.subAggregation(attr_id_agg);
        //2.1.1 在每个属性ID下，按照属性名进行聚合
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        //2.1.1 在每个属性ID下，按照属性值进行聚合
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        searchSourceBuilder.aggregation(attr_agg);
        String dsl = searchSourceBuilder.toString();
        log.debug("构建的DSL语句 {}",searchSourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
        return searchRequest;
    }

    private SearchResult getSearchResult(SearchResponse searchResponse, SearchParam param){
        SearchResult searchResult = new SearchResult();
        SearchHits hits = searchResponse.getHits();
        List<SkuEsModel> skuEsModels = new ArrayList<>();
        if (hits.getHits()!=null&&hits.getHits().length>0){
            for (SearchHit hit: hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                if (!StringUtils.isEmpty(param.getKeyword())){
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    String skuTitleValue = skuTitle.getFragments()[0].string();
                    skuEsModel.setSkuTitle(skuTitleValue);
                }
                skuEsModels.add(skuEsModel);
            }
        }
        searchResult.setProduct(skuEsModels);

        List<SearchResult.AttrVo> attrVos = new ArrayList<>();
        ParsedNested attrsAgg = searchResponse.getAggregations().get("attr_agg");
        ParsedLongTerms attrIdAgg = attrsAgg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket: attrIdAgg.getBuckets()
             ) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            long attrId = bucket.getKeyAsNumber().longValue();
            attrVo.setAttrId(attrId);

            ParsedStringTerms attrNameAgg = bucket.getAggregations().get("attr_name_agg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            attrVo.setAttrName(attrName);

            ParsedStringTerms attrValueAgg = bucket.getAggregations().get("attr_value_agg");
            HashSet<String> attrVs = new HashSet<>();
            attrValueAgg.getBuckets().stream().map(item->{
                String[] split = item.getKeyAsString().split(";");
                List<String> strings = Arrays.asList(split);
                attrVs.addAll(strings);
                return strings;
            }).collect(Collectors.toList());
            ArrayList<String> attrValues = new ArrayList<>();
            attrValues.addAll(attrVs);
            attrVo.setAttrValue(attrValues);
            attrVos.add(attrVo);
            
        }
        searchResult.setAttrs(attrVos);

        List<SearchResult.BrandVo> brandVos = new ArrayList<>();
        ParsedLongTerms brandAgg = searchResponse.getAggregations().get("brand_agg");

        for (Terms.Bucket bucket: brandAgg.getBuckets()
             ) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            long brandId = bucket.getKeyAsNumber().longValue();
            brandVo.setBrandId(brandId);
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brand_name_agg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandName(brandName);
            ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brand_img_agg");
            String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandImg(brandImg);
            brandVos.add(brandVo);
        }
        searchResult.setBrands(brandVos);

        ArrayList<SearchResult.CatelogVo> catelogVos = new ArrayList<>();
        ParsedLongTerms catelogAgg = searchResponse.getAggregations().get("catelog_agg");
        for (Terms.Bucket bucket:catelogAgg.getBuckets()
             ) {
            SearchResult.CatelogVo catelogVo = new SearchResult.CatelogVo();
            String catelogId = bucket.getKeyAsString();
            catelogVo.setCatelogId(Long.parseLong(catelogId));
            ParsedStringTerms catelogNameAgg = bucket.getAggregations().get("catelog_name_agg");
            String catelogName = catelogNameAgg.getBuckets().get(0).getKeyAsString();
            catelogVo.setCatelogName(catelogName);
            catelogVos.add(catelogVo);
        }
        searchResult.setCatelogs(catelogVos);

        searchResult.setPageNum(param.getPageNum());
        long total = hits.getTotalHits().value;
        searchResult.setTotal(total);
        int totalPages = (int) (total % EsConstant.PRODUCT_PAGESIZE)== 0 ? (int) (total / EsConstant.PRODUCT_PAGESIZE) : (int) (total / EsConstant.PRODUCT_PAGESIZE + 1);
        searchResult.setTotalPages(totalPages);
        ArrayList<Integer> pageNavs = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            pageNavs.add(i);
        }
        searchResult.setPageNavs(pageNavs);

        if (param.getAttrs()!=null&&param.getAttrs().size()>0){
            List<SearchResult.NavVo> collect = param.getAttrs().stream().map(attr -> {
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);
                R r = productFeignClient.info(Long.parseLong(s[0]));
                if (r.getCode() == 0) {
                    AttrResponseVo data = r.getData(new TypeReference<AttrResponseVo>() {
                    });
                    navVo.setNavName(data.getAttrName());
                } else {
                    navVo.setNavName(s[0]);
                }
                String encode = null;
                try {
                    encode = URLEncoder.encode(attr, "UTF-8");
                    encode.replace("+", "%20");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String replace = param.get_queryString().replace("&attrs=" + attr, "");
                navVo.setLink("http://search.gulimall.com/list.html?" + replace);

                return navVo;
            }).collect(Collectors.toList());
            searchResult.setNavs(collect);
        }
        return searchResult;
    }

}
