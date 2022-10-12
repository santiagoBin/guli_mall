package com.atguigu.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.entity.vo.Catelog2Vo;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        List<CategoryEntity> collect = categoryEntities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(0L);
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, categoryEntities));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() != null ? menu1.getSort() : 0) - (menu2.getSort() != null ? menu2.getSort() : 0);
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<CategoryBrandRelationEntity>().in("catelog_id", asList);
        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.list(wrapper);
        if (list.size()==0){
            baseMapper.deleteBatchIds(asList);
        }else {
            throw new RuntimeException("该分类下有关联品牌存在，无法删除该分类。。。");
        }
    }

    /*
    * 收集某个category在tree中的全路径
    * */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        ArrayList<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }
/*
* 级联更新所有相关数据
* */

    @CacheEvict(value = "category",allEntries = true)
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }


    @Cacheable(value = "category",key = "#root.methodName",sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> list = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return list;

    }

    @Override
    @Cacheable(value = "category",key = "#root.methodName")
    public Map<String, List<Catelog2Vo>> getIndexJsonData() {
            List<CategoryEntity> totalList = this.list();
            List<CategoryEntity> list = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0).select("cat_id"));
            Map<String, List<Catelog2Vo>> map = list.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                AtomicReference<Integer> indexForCate2 = new AtomicReference<>(1);
                AtomicReference<Integer> indexForCate3 = new AtomicReference<>(1);
                List<Catelog2Vo> catelog2VoList = totalList.stream().filter(cate -> {
                    return cate.getParentCid().equals(v.getCatId());
                }).map((forCate2) -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo();
                    catelog2Vo.setCatelog1Id(v.getCatId().toString());
                    catelog2Vo.setId(indexForCate2.toString());
                    indexForCate2.getAndSet(indexForCate2.get() + 1);
                    catelog2Vo.setName(forCate2.getName());
                    List<Catelog2Vo.Catelog3Vo> catelog3VoList = totalList.stream().filter(cate -> {
                        return cate.getParentCid().equals(forCate2.getCatId());
                    }).map(forCate3 -> {
                        Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                        catelog3Vo.setCatelog2Id(catelog2Vo.getId());
                        catelog3Vo.setId(forCate3.getCatId().toString());
                        catelog3Vo.setName(forCate3.getName());
                        indexForCate3.getAndSet(indexForCate3.get() + 1);
                        return catelog3Vo;
                    }).collect(Collectors.toList());
                    catelog2Vo.setCatelog3List(catelog3VoList);
                    return catelog2Vo;
                }).collect(Collectors.toList());
                return catelog2VoList;
            }));
            return map;
    }



    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

        //1、占分布式锁。去redis占坑
        //（锁的粒度，越细越快:具体缓存的是某个数据，11号商品） product-11-lock
        //RLock catalogJsonLock = redissonClient.getLock("catalogJson-lock");
        //创建读锁
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("catalogJson-lock");

        RLock rLock = readWriteLock.readLock();

        Map<String, List<Catelog2Vo>> dataFromDb = null;
        try {
            rLock.lock();
            //加锁成功...执行业务
            dataFromDb = getIndexJsonDataFromDb();
        } finally {
            rLock.unlock();
        }
        //先去redis查询下保证当前的锁是自己的
        //获取值对比，对比成功删除=原子性 lua脚本解锁
        // String lockValue = stringRedisTemplate.opsForValue().get("lock");
        // if (uuid.equals(lockValue)) {
        //     //删除我自己的锁
        //     stringRedisTemplate.delete("lock");
        // }

        return dataFromDb;

    }

    public Map<String, List<Catelog2Vo>> getIndexJsonDataFromDbWithRedisLock(){
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock){
            System.out.println("获取分布式锁成功");
            Map<String, List<Catelog2Vo>> indexJsonDataFromDb;
            try {
                indexJsonDataFromDb = getIndexJsonDataFromDb();
            }finally {
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<Long>(script,Long.class),Arrays.asList("lock"),uuid);
            }
            return indexJsonDataFromDb;
        }else {
            System.out.println("获取分布式锁失败...等待重试");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getIndexJsonDataFromDbWithRedisLock();
        }
    }

    public Map<String, List<Catelog2Vo>> getIndexJsonDataFromDbWithLocalLock(){
//        String catelogJSON = redisTemplate.opsForValue().get("catelogJSON");
//        if (StringUtils.isEmpty(catelogJSON)){
//            Map<String, List<Catelog2Vo>> indexJsonDataFromDb = getIndexJsonDataFromDb();
//            String s = JSON.toJSONString(indexJsonDataFromDb);
//            redisTemplate.opsForValue().set("catelogJSON",s);
//            return indexJsonDataFromDb;
//        }
//        System.out.println("缓存命中");
//        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
//        return result;
        synchronized (this){
            return getIndexJsonDataFromDb();
        }
    }


    //从数据库查询并封装数据
    public Map<String, List<Catelog2Vo>> getIndexJsonDataFromDb() {
        String catelogJSON = redisTemplate.opsForValue().get("catelogJSON");
        if (StringUtils.isEmpty(catelogJSON)){
            System.out.println("查询数据库");
            List<CategoryEntity> totalList = this.list();
            List<CategoryEntity> list = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0).select("cat_id"));
            Map<String, List<Catelog2Vo>> map = list.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                AtomicReference<Integer> indexForCate2 = new AtomicReference<>(1);
                AtomicReference<Integer> indexForCate3 = new AtomicReference<>(1);
                List<Catelog2Vo> catelog2VoList = totalList.stream().filter(cate -> {
                    return cate.getParentCid().equals(v.getCatId());
                }).map((forCate2) -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo();
                    catelog2Vo.setCatelog1Id(v.getCatId().toString());
                    catelog2Vo.setId(indexForCate2.toString());
                    indexForCate2.getAndSet(indexForCate2.get() + 1);
                    catelog2Vo.setName(forCate2.getName());
                    List<Catelog2Vo.Catelog3Vo> catelog3VoList = totalList.stream().filter(cate -> {
                        return cate.getParentCid().equals(forCate2.getCatId());
                    }).map(forCate3 -> {
                        Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                        catelog3Vo.setCatelog2Id(forCate2.getCatId().toString());
                        catelog3Vo.setId(indexForCate3.toString());
                        catelog3Vo.setName(forCate3.getName());
                        indexForCate3.getAndSet(indexForCate3.get() + 1);
                        return catelog3Vo;
                    }).collect(Collectors.toList());
                    catelog2Vo.setCatelog3List(catelog3VoList);
                    return catelog2Vo;
                }).collect(Collectors.toList());
                return catelog2VoList;
            }));
            String s = JSON.toJSONString(map);
            redisTemplate.opsForValue().set("catelogJSON",s);
            return map;
        }
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        return result;
    }

    private List<Long> findParentPath(Long cateLogId,List<Long> paths){
        paths.add(cateLogId);
        CategoryEntity byId = this.getById(cateLogId);
        if (byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }

    private List<CategoryEntity> getChildren(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> collect = categoryEntities.stream().filter(category -> {
            return category.getParentCid().equals(categoryEntity.getCatId());
        }).map(category -> {
            if (category == null) {
                return null;
            } else {
                category.setChildren(getChildren(category, categoryEntities));
                return category;
            }
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() != null ? menu1.getSort() : 0) - (menu2.getSort() != null ? menu2.getSort() : 0);
        }).collect(Collectors.toList());
        return collect;
    }
}