package com.atguigu.gulimall.product.app;

import java.util.List;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.product.entity.vo.BrandVo;
import com.atguigu.gulimall.product.entity.vo.CatelogVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:11:54
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("catelog/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam("brandId") String brandId){
        List<CategoryBrandRelationEntity>  data= categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return R.ok().put("data", data);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody CatelogVo catelogVo){
        categoryBrandRelationService.removeRelation(catelogVo);
        return R.ok();
    }

    @GetMapping("brands/list")
    public R getCateRelationBrands(@RequestParam String catId){
        List<BrandVo> list = categoryBrandRelationService.getCateRelationBrands(catId);
        return R.ok().put("data",list);
    }

    @GetMapping("catelog/list")
    public R getBrandRelationCategory(String brandId){
        List<CatelogVo> list = categoryBrandRelationService.getBrandRelationCategory(brandId);
        return R.ok().put("data",list);
    }



}
