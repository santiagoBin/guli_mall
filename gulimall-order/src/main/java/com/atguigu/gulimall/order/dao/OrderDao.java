package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:56:05
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
