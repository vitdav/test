package com.sgugo.sky.mapper;

import com.sgugo.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 套餐和菜品关联关系
 */
@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品Id,查询对应的套餐id
     * @param dishIds 菜品id集合
     * @return 套餐id集合
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);


    /**
     * 批量保存套餐和菜品的关联关系
     * @param setmealDishes 包含套餐id的菜品信息
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id，删除套餐和菜品的关联关系
     * @param setmealId 套餐id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

}
