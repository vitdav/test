package com.sgugo.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品Id,查询对应的套餐id
     * @param dishIds 菜品id集合
     * @return 套餐id集合
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
