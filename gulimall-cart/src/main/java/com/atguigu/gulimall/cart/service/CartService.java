package com.atguigu.gulimall.cart.service;

import com.atguigu.gulimall.cart.vo.CartItemVo;
import com.atguigu.gulimall.cart.vo.CartVo;
import com.atguigu.gulimall.common.to.cart.CartItemInOrderTo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CartService {
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItemVo getCartItemVo(Long skuId);

    CartVo getCart();

    void checkItem(Long skuId, Integer check);

    void changeItemNum(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemInOrderTo> getUserCurrentCartItems();
}
