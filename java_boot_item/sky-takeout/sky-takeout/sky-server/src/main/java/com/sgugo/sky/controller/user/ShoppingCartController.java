package com.sgugo.sky.controller.user;

import com.sgugo.sky.dto.ShoppingCartDTO;
import com.sgugo.sky.entity.ShoppingCart;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name="ShoppingCart-购物车")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO 商品信息
     * @return 是否添加成功
     */
    @PostMapping("/add")
    @Operation(summary = "add->添加购物车")
    public R add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车，商品信息为：{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return R.success();
    }


    /**
     * 查看购物车
     * @return 购物车信息
     */
    @GetMapping("/list")
    @Operation(summary = "list->查看购物车")
    public R<List<ShoppingCart>> list(){
        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
        return R.success(list);
    }

    /**
     * 删除购物车中的一个商品
     * @param shoppingCartDTO 被删除的商品信息
     * @return 删除是否成功
     */
    @PostMapping("/sub")
    @Operation(summary = "sub->删除一个商品")
    public R sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("删除购物车中一个商品，商品：{}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return R.success();
    }

    /**
     * 清空购物车
     * @return 是否清空成功
     */
    @DeleteMapping("/clean")
    @Operation(summary = "clean->清空购物车")
    public R clean(){
        shoppingCartService.cleanShoppingCart();
        return R.success();
    }
}
