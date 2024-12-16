package com.sgugo.sky.controller.user;

import com.sgugo.sky.constant.StatusConstant;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.DishService;
import com.sgugo.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Dish-菜品浏览")
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类id
     * @return 查询到的菜品列表
     */
    @Operation(summary="list->查询菜品")
    @GetMapping("/list")
    @Parameter(name="categoryId",description = "分类id",example="11")
    public R<List<DishVO>> list(Long categoryId){
        //构造 redis 中的 key
        String key = "dish" + categoryId;

        //先从Redis中获取缓存的菜品数据，没有缓存再向数据库获取数据
        List<DishVO> list =  (List<DishVO>) redisTemplate.opsForValue().get(key);

        //判断是否存在缓存数据
        if(list != null && list.size() > 0){

            return R.success(list);
        }

        //缓存中不存在数据，则向数据行中查询菜品信息

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        //只查询ENABLE（启用）状态的菜品
        dish.setStatus(StatusConstant.ENABLE);
        //调用Service处理业务
        list = dishService.listWithFlavor(dish);


        //将查询到的数据缓存到Redis
        redisTemplate.opsForValue().set(key, list);

        return R.success(list);
    }
}
