package com.atguigu.gulimall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.vo.AttrGroupCateRelationVo;
import com.atguigu.gulimall.product.entity.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.R;



/**
 * 属性分组
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:11:54
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    AttrService attrService;
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 列表
     */
    @RequestMapping("/list/{cateId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@PathVariable(value = "cateId",required = false) String cateId,@RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.queryPage(cateId,params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable(value = "attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeAttrGroupAndRelation(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> list = attrService.getAttrRelation(attrgroupId);
        return R.ok().put("data",list);
    }

    @PostMapping("attr/relation/delete")
    public R removeAttrGroupRelation(@RequestBody AttrGroupRelationVo[] relationVos){
        attrGroupService.removeRelations(relationVos);
        return R.ok();
    }

    @GetMapping("{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable String attrgroupId,@RequestParam Map<String,Object> params){
        PageUtils pageUtils = attrGroupService.queryNoRelationAttr(attrgroupId,params);
        return R.ok().put("page",pageUtils);
    }

    @PostMapping("attr/relation")
    public R saveAttrRelation(@RequestBody List<AttrGroupRelationVo> relationVos){
        attrGroupService.saveAttrRelationBatch(relationVos);
        return R.ok();
    }

    @GetMapping("/{catelogId}/withattr")
    public R getCateRelationAttrGroupAndAttrs(@PathVariable String catelogId) {
        List<AttrGroupCateRelationVo> list = categoryBrandRelationService.getCateRelationAttrGroupAndAttrs(catelogId);
        return R.ok().put("data", list);
    }
}
