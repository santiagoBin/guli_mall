package com.atguigu.gulimall.member.service;

import com.atguigu.gulimall.common.to.UserLoginTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:47:05
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R login(UserLoginTo userLoginTo);

    R gitLogin(MemberRespVo memberRespVo);

    R wxLogin(MemberRespVo memberRespVo);
}

