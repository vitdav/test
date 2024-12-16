package com.sgugo.sky.controller.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sgugo.sky.dto.DishDTO;
import com.sgugo.sky.dto.DishPageQueryDTO;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.result.R;
import com.sgugo.sky.result.Rs;
import com.sgugo.sky.service.DishService;
import com.sgugo.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Tag(name="Dish-菜品管理")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO DTO
     * @return R
     */
    @PostMapping
    @Operation(summary="save->新增菜品")
    @ApiOperationSupport(order=1)
    public R save(@RequestBody DishDTO dishDTO){
        dishService.saveWithFlavor(dishDTO);

        //清理缓存数据：分类里新增了菜品，该分类更新了，因此要清理缓存
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

        return R.success();
    }

    /**
     * 菜品分页查询
     * @param dto 分页查询条件的dto
     * @return 分页查询的结果，Rs是用来返回分页数据的
     */
    @GetMapping("/page")
    @Operation(summary="page->分页查询菜品")
    @ApiOperationSupport(order=2)
    public R<Rs> page(@ParameterObject DishPageQueryDTO dto){
        Rs rs = dishService.pageQuery(dto);
        return R.success(rs);
    }

    /**
     * 菜品批量删除
     * @param ids 待删除的菜品id集合
     * @return R
     */
    @DeleteMapping
    @Operation(summary="delete->删除菜品")
    @ApiOperationSupport(order=3)
    public R delete(@RequestParam List<Long> ids){
        dishService.deleteBatch(ids);

        //将所有菜品缓存数据清理掉：判断是哪个分类删除了菜品太小号资源了
        cleanCache("dish_*");

        return R.success();
    }

    /**
     * 根据id查询菜品（包含口味信息）
     * @param id 菜品的id
     * @return 菜品的信息
     */
    @GetMapping("/{id}")
    @Operation(summary="getById->查询菜品")
    @ApiOperationSupport(order=4)
    public R<DishVO> getById(@PathVariable Long id){
        DishVO vo = dishService.getByIdWithFlavor(id);
        return R.success(vo);
    }

    /**
     * 修改菜品（包含口味）
     * @param dishDTO dto
     * @return R
     */
    @PutMapping
    @Operation(summary="update->修改菜品")
    @ApiOperationSupport(order=5)
    public R update(@RequestBody DishDTO dishDTO){
        dishService.updateWithFlavor(dishDTO);

        //修改菜品后，将所有的菜品缓存数据清空
        cleanCache("dish_*");

        return R.success();
    }

    /**
     * 菜品的起售和停售
     * @param status 修改后的菜品状态
     * @param id 菜品的id
     * @return R
     */
    @PostMapping("/status/{status}")
    @Operation(summary="startOrStop->修改菜品状态")
    public R<String> startOrStop(@PathVariable Integer status, Long id){
        dishService.startOrStop(status,id);

        //修改菜品状态后，将所有的菜品缓存清理
        cleanCache("dish_*");

        return R.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类id
     * @return 菜品列表信息
     */
    @GetMapping("/list")
    @Operation(summary = "list->根据分类id查询菜品")
    public R<List<Dish>> list(long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return R.success(list);
    }

    /**
     * 清理缓存数据
     * @param pattern 待清理的key
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
