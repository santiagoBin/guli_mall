package com.atguigu.gulimall.order.feign;

import com.atguigu.gulimall.common.to.member.MemberAddrTo;
import com.atguigu.gulimall.common.to.member.MemberTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gulimall-member")
public interface MemberFeignClient {
    @GetMapping("member/memberreceiveaddress/{memberId}/getMemberAddressByMemberId")
    public List<MemberAddrTo> getMemberAddress(@PathVariable("memberId") Long memberId);

    @GetMapping("member/member/getMemberById")
    public MemberTo getMemberById(@RequestParam("memberId") String memberId);
}
