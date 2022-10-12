package com.atguigu.gulimall.order.vo;

import com.atguigu.gulimall.common.to.member.MemberAddrTo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-04 23:19
 **/

@Data
public class FareVo {

    private MemberAddrTo address;

    private BigDecimal fare;

}
