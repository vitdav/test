package com.sgugo.sky.controller.user;

import com.sgugo.sky.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Tag(name="Shop-营业状态")
public class ShopController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public static final String KEY = "SHOP_STATUS";

    /**
     * 获取店铺的营业状态
     * @return R
     */
    @GetMapping("/status")
    @Operation(summary = "getStatus->获取营业状态")
    public R<Integer> getStatus(){
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Integer status = (Integer)ops.get(KEY);
        return R.success(status);
    }
}
