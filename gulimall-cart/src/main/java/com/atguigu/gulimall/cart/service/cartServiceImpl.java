package com.atguigu.gulimall.cart.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.cart.feign.ProductFeignClient;
import com.atguigu.gulimall.cart.interceptor.CartInterceptor;
import com.atguigu.gulimall.cart.to.UserInfoTo;
import com.atguigu.gulimall.cart.vo.CartItemVo;
import com.atguigu.gulimall.cart.vo.CartVo;
import com.atguigu.gulimall.common.to.cart.CartItemInOrderTo;
import com.atguigu.gulimall.common.to.cart.SkuInfoTo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.atguigu.gulimall.common.constant.CartConstant.CART_PREFIX;

@Service
public class cartServiceImpl implements CartService{
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String productRedisValue = (String)cartOps.get(skuId.toString());
        if (StringUtils.isEmpty(productRedisValue)){
            CartItemVo cartItemVo = new CartItemVo();
            CompletableFuture<Void> getSkuInfoFuture = CompletableFuture.runAsync(() -> {
                SkuInfoTo skuInfoBySkuId = productFeignClient.getSkuInfoBySkuId(skuId);
                cartItemVo.setSkuId(skuId);
                cartItemVo.setTitle(skuInfoBySkuId.getSkuTitle());
                cartItemVo.setPrice(skuInfoBySkuId.getPrice());
                cartItemVo.setCount(num);
                cartItemVo.setImage(skuInfoBySkuId.getSkuDefaultImg());
            }, executor);

            CompletableFuture<Void> getSkuSaleAttrsFuture = CompletableFuture.runAsync(() -> {
                List<String> skuSaleAttrListBySkuId = productFeignClient.getSkuSaleAttrListBySkuId(skuId);
                cartItemVo.setSkuAttrValues(skuSaleAttrListBySkuId);
            }, executor);

            CompletableFuture.allOf(getSkuInfoFuture,getSkuSaleAttrsFuture).get();

            String s = JSON.toJSONString(cartItemVo);
            cartOps.put(skuId.toString(),s);

            return cartItemVo;

        }else{
            CartItemVo cartItemVo = JSON.parseObject(productRedisValue, CartItemVo.class);
            cartItemVo.setCount(cartItemVo.getCount()+num);
            String s = JSON.toJSONString(cartItemVo);
            cartOps.put(skuId.toString(),s);
            return cartItemVo;
        }
    }

    @Override
    public CartItemVo getCartItemVo(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String s = (String) cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(s, CartItemVo.class);
        return cartItemVo;
    }

    @Override
    public CartVo getCart() {
        CartVo cartVo = new CartVo();
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() != null){
            String cartLoginKey = CART_PREFIX + userInfoTo.getUserId();
            String cartTempKey = CART_PREFIX + userInfoTo.getUserKey();
            List<CartItemVo> tempCartList = getCartItems(cartTempKey);
            if (tempCartList !=null){
                tempCartList.forEach(cartItemVo -> {
                    if (cartItemVo != null){
//                        System.out.println(123);
                        try {
                            addToCart(cartItemVo.getSkuId(),cartItemVo.getCount());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
//                System.out.println(cartTempKey);
                clearCartInfo(cartTempKey);
            }
            List<CartItemVo> cartItems = getCartItems(cartLoginKey);
            cartVo.setItems(cartItems);
        }else {
            String cartKey = CART_PREFIX + userInfoTo.getUserKey();
            List<CartItemVo> cartItems = getCartItems(cartKey);
            cartVo.setItems(cartItems);
        }
        return cartVo;
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String s = (String)cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(s, CartItemVo.class);
        cartItemVo.setCheck(check ==1?true:false);
        String s1 = JSON.toJSONString(cartItemVo);
        cartOps.put(skuId.toString(),s1);
    }

    @Override
    public void changeItemNum(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String s = (String)cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(s, CartItemVo.class);
        cartItemVo.setCount(num);
        String s1 = JSON.toJSONString(cartItemVo);
//        System.out.println(s1);
        cartOps.put(skuId.toString(),s1);
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId);
    }

    @Override
    public List<CartItemInOrderTo> getUserCurrentCartItems() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String cartKey = CART_PREFIX + userInfoTo.getUserId();
        List<CartItemVo> cartItems = getCartItems(cartKey);
        if (cartItems != null&&cartItems.size()>0){
            List<CartItemInOrderTo> collect = cartItems.stream().filter(item->{
                return item.getCheck();
            }).map(cartItemVo -> {
                CartItemInOrderTo cartItemInOrderTo = new CartItemInOrderTo();
                BigDecimal price = productFeignClient.getPrice(cartItemVo.getSkuId());
                cartItemVo.setPrice(price);
                BeanUtils.copyProperties(cartItemVo, cartItemInOrderTo);
                return cartItemInOrderTo;
            }).collect(Collectors.toList());
            return collect;
        }else {
            return null;
        }
    }

    private void clearCartInfo(String cartTempKey) {
        redisTemplate.delete(cartTempKey);
    }

    private List<CartItemVo> getCartItems(String cartKey) {
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(cartKey);
        List<Object> values = ops.values();
        if (values != null&& values.size()>0){
            List<CartItemVo> collect = values.stream().map(value -> {
                String s = (String) value;
                CartItemVo cartItemVo = JSON.parseObject(s, CartItemVo.class);
                return cartItemVo;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }


    private BoundHashOperations<String,Object,Object> getCartOps(){
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String cartRedisKey = "" ;
        if (userInfoTo.getUserId() != null){
            cartRedisKey = CART_PREFIX + userInfoTo.getUserId();
        }else {
            cartRedisKey = CART_PREFIX + userInfoTo.getUserKey();
        }
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(cartRedisKey);
        return ops;
    }
}
