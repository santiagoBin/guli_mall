package com.atguigu.gulimall.member.service.impl;

import com.atguigu.gulimall.common.to.member.MemberAddrTo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.member.dao.MemberReceiveAddressDao;
import com.atguigu.gulimall.member.entity.MemberReceiveAddressEntity;
import com.atguigu.gulimall.member.service.MemberReceiveAddressService;


@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberReceiveAddressEntity> page = this.page(
                new Query<MemberReceiveAddressEntity>().getPage(params),
                new QueryWrapper<MemberReceiveAddressEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MemberAddrTo> getListById(Long memberId) {
        List<MemberReceiveAddressEntity> member_id = this.list(new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id", memberId));
        List<MemberAddrTo> collect = member_id.stream().map(memberAddr -> {
            MemberAddrTo memberAddrTo = new MemberAddrTo();
            BeanUtils.copyProperties(memberAddr, memberAddrTo);
            return memberAddrTo;
        }).collect(Collectors.toList());
        return collect;
    }

}