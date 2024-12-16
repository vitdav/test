package com.sgugo.sky.service;

import com.sgugo.sky.dto.SetmealDTO;
import com.sgugo.sky.dto.SetmealPageQueryDTO;
import com.sgugo.sky.entity.Setmeal;
import com.sgugo.sky.vo.DishItemVO;
import com.sgugo.sky.vo.PageResultVO;
import com.sgugo.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO DTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     *  根据条件(分类id)查询套餐
     * @param setmeal 查询条件
     * @return 套餐列表信息
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id，查询套餐内的菜品信息
     * @param id 套餐id
     * @return 套餐内包含的菜品信息
     */
    List<DishItemVO> getDishItemById(Long id);

    /**
     * 分页查询
     * @param setmealPageQueryDTO 查询条件
     * @return 查询结果
     */
    PageResultVO pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 删除套餐（可批量删除）
     * @param ids 待删除的id列表
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据套餐id，查询套餐信息和套餐关联的菜品信息
     * @param id 套餐id
     * @return 套餐信息
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 修改套餐
     * @param setmealDTO 修改后的套餐信息
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 修改套餐状态
     * @param status 修改后的状态，1启售 0停售
     * @param id 套餐id
     */
    void startOrStop(Integer status, Long id);
}
