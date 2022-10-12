package com.atguigu.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.common.exception.BizCodeEnum;
import com.atguigu.gulimall.common.to.UserLoginTo;
import com.atguigu.gulimall.common.to.UserRegisterTo;
import com.atguigu.gulimall.common.to.member.MemberTo;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.atguigu.gulimall.member.service.MemberService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.R;



/**
 * 会员
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:47:05
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    public boolean existMemberOfPhone(String phone){
        int count = memberService.count(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        return count>0;
    }

    public boolean existMemberOfUserName(String userName){
        int count = memberService.count(new QueryWrapper<MemberEntity>().eq("username", userName));
        return count>0;
    }

    @PostMapping("register")
    public R register(@RequestBody UserRegisterTo userRegisterTo){
        R r = R.error(BizCodeEnum.REGISTER_EXCEPTION.getCode(), BizCodeEnum.REGISTER_EXCEPTION.getMsg());
        boolean flag = false;
        if (existMemberOfPhone(userRegisterTo.getPhone())){
            flag = true;
            r.put("phone","该手机号已经注册过了，请更换手机号或直接登录。");
        }
        if (existMemberOfUserName(userRegisterTo.getUserName())){
            flag = true;
            r.put("userName","昵称已存在，请重新输入。");
        }
        if (flag){
            return r;
        }else {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUsername(userRegisterTo.getUserName());
            memberEntity.setNickname(userRegisterTo.getUserName());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            memberEntity.setPassword(bCryptPasswordEncoder.encode(userRegisterTo.getPassword()));
            memberEntity.setMobile(userRegisterTo.getPhone());
            memberService.save(memberEntity);
            return R.ok();
        }
    }

    @PostMapping("login")
    public R login(@RequestBody UserLoginTo userLoginTo){
        R r = memberService.login(userLoginTo);
        return r;
    }

    @PostMapping("github/oauth2/login")
    public R gitLogin(@RequestBody MemberRespVo memberRespVo){
        R r = memberService.gitLogin(memberRespVo);
        return r;

    }

    @PostMapping("wx/login")
    public R wxLogin(@RequestBody MemberRespVo memberRespVo){
        R r = memberService.wxLogin(memberRespVo);
        return r;
    }

    @GetMapping("getMemberById")
    public MemberTo getMemberById(@RequestParam("memberId") String memberId){
        MemberEntity memberEntity = memberService.getById(memberId);
        MemberTo memberTo = new MemberTo();
        BeanUtils.copyProperties(memberEntity,memberTo);
        return memberTo;
    }

}
