package com.atguigu.gulimall.member.service.impl;

import com.atguigu.gulimall.common.exception.BizCodeEnum;
import com.atguigu.gulimall.common.to.UserLoginTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.member.dao.MemberDao;
import com.atguigu.gulimall.member.entity.MemberEntity;
import com.atguigu.gulimall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R login(UserLoginTo userLoginTo) {
        MemberEntity memberEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("mobile", userLoginTo.getLoginAccount()).or().eq("username", userLoginTo.getLoginAccount()));
        if (memberEntity!=null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(userLoginTo.getPassword(), memberEntity.getPassword());
            if (matches){
                R r = R.ok();
                r.setData(memberEntity);
                return r;
            }else {
                return R.error(BizCodeEnum.LOGIN_ERROR_PASSWD_EXCEPTION.getCode(), BizCodeEnum.LOGIN_ERROR_PASSWD_EXCEPTION.getMsg());
            }
        }else {
            return R.error(BizCodeEnum.LOGIN_ACCOUNT_NOT_FOUND_EXCEPTION.getCode(), BizCodeEnum.LOGIN_ACCOUNT_NOT_FOUND_EXCEPTION.getMsg());
        }
    }

    @Override
    public R gitLogin(MemberRespVo memberRespVo) {
        MemberEntity memberEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("social_uid", "github_oauth_user_"+memberRespVo.getId()));
        if (memberEntity!=null){
            memberRespVo.setId(memberEntity.getId().toString());
            return R.ok().setData(memberRespVo);
        }else {
            MemberEntity memberEntity1 = new MemberEntity();
            memberEntity1.setNickname(memberRespVo.getNickname());
            memberEntity1.setUsername(memberRespVo.getUsername());
            memberEntity1.setSocialUid("github_oauth_user_"+memberRespVo.getId().toString());
            this.save(memberEntity1);
            memberRespVo.setId(memberEntity1.getId().toString());
            return R.ok().setData(memberRespVo);
        }
    }

    @Override
    public R wxLogin(MemberRespVo memberRespVo) {
        MemberEntity member = this.getOne(new QueryWrapper<MemberEntity>().eq("social_uid", "wx_oauth_user_"+memberRespVo.getId()));
        if (member!=null){
            memberRespVo.setId(member.getId().toString());
            return R.ok().setData(memberRespVo);
        }else {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setNickname(memberRespVo.getNickname());
            memberEntity.setUsername(memberRespVo.getUsername());
            memberEntity.setSocialUid("wx_oauth_user_"+memberRespVo.getId());
            this.save(memberEntity);
            memberRespVo.setId(memberEntity.getId().toString());
            return R.ok().setData(memberRespVo);
        }
    }

}