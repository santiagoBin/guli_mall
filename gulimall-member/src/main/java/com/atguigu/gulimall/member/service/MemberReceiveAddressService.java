package com.atguigu.gulimall.member.service;

import com.atguigu.gulimall.common.to.member.MemberAddrTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.member.entity.MemberReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:47:05
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MemberAddrTo> getListById(Long memberId);
}

