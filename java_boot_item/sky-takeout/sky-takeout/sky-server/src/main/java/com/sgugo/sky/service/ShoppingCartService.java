package com.sgugo.sky.service;

import com.sgugo.sky.dto.ShoppingCartDTO;
import com.sgugo.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO 待添加的内容
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return 购物车信息
     */
    List<ShoppingCart> showShoppingCart();


    /**
     * 删除购物车中的一个商品
     * @param shoppingCartDTO 被删除的商品信息
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车
     */
    void cleanShoppingCart();
}
