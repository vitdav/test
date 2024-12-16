package com.sgugo.sky.controller.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sgugo.sky.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Tag(name="Shop-营业状态")
@Slf4j
public class ShopController {

    //定义店铺状态的常量
    public static final String KEY = "SHOP_STATUS";

    //引入Redis
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺的营业状态
     * @param status 状态标识：1=营业中，0=打烊
     * @return R
     */
    @PutMapping("/{status}")
    @Operation(summary="setStatus->设置店铺状态")
    @ApiOperationSupport(order=1)
    public R setStatus(@PathVariable int status){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(KEY,status);
        return R.success();
    }

    /**
     * 获取店铺的营业状态
     * @return R
     */
    @GetMapping("/status")
    @Operation(summary = "getStatus->获取店铺状态")
    @ApiOperationSupport(order=2)
    public R<Integer> getStatus(){
        ValueOperations ops = redisTemplate.opsForValue();
        Integer status = (Integer)ops.get(KEY);
        System.out.println(status.getClass());
        return R.success(status);
    }
}
