package com.sgugo.sky.controller.user;

import com.sgugo.sky.constant.StatusConstant;
import com.sgugo.sky.entity.Setmeal;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.SetmealService;
import com.sgugo.sky.vo.DishItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Setmeal-套餐")
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类查询套餐
     * @param categoryId 分类id
     * @return 套餐列表信息
     */
    @GetMapping("/list")
    @Operation(summary = "list->根据分类查询套餐")
    @Cacheable(cacheNames = "setmealCache",key="#categoryId")
    @Parameter(name="categoryId",description = "套餐分类id",example = "10")
    public R<List<Setmeal>> list(Long categoryId){
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return R.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     * @param id 套餐id
     * @return 套餐内包含的菜品列表
     */
    @GetMapping("/dish/{id}")
    @Operation(summary = "dishList->查询套餐内菜品")
    @Parameter(name="id",description = "套餐id",example = "1")
    public R<List<DishItemVO>> dishList(@PathVariable("id") Long id){
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return R.success(list);
    }
}
