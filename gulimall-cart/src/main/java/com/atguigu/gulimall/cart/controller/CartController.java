package com.atguigu.gulimall.cart.controller;

import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.vo.CartItemVo;
import com.atguigu.gulimall.cart.vo.CartVo;
import com.atguigu.gulimall.common.to.cart.CartItemInOrderTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("getUserCurrentCartItems")
    @ResponseBody
    public List<CartItemInOrderTo> getUserCurrentCartItems(){
        return cartService.getUserCurrentCartItems();
    }
    @GetMapping("/cartlist.html")
    public String cartListPage(){

        return "cartlist";
    }

    @GetMapping("addCartItem")
    public String addToCart(RedirectAttributes redirectAttributes, @RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, Model model) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId,num);
        redirectAttributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccessPage.html";
    }

    @GetMapping("addToCartSuccessPage.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,Model model){
        CartItemVo cartItemVo = cartService.getCartItemVo(skuId);
        model.addAttribute("cartItem",cartItemVo);
        return "success";
    }


    @GetMapping("cart.html")
    public String getCart(Model model){
        CartVo cartVo = cartService.getCart();
        model.addAttribute("cart",cartVo);
        return "cartlist";
    }

    @GetMapping("checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,@RequestParam("checked") Integer check){
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    @GetMapping("countItem")
    public String countItem(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num){
        cartService.changeItemNum(skuId,num);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    @GetMapping("deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }
}
