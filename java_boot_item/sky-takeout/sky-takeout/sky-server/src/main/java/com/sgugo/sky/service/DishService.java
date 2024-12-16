package com.sgugo.sky.service;

import com.sgugo.sky.dto.DishDTO;
import com.sgugo.sky.dto.DishPageQueryDTO;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.result.Rs;
import com.sgugo.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    /**
     * 新增菜品和对应的口味
     * @param dishDTO DTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dto dto
     * @return 分页查询的结果
     */
    Rs pageQuery(DishPageQueryDTO dto);

    /**
     * 菜品删除（批量）
     * @param ids 菜品id集合
     */
    void deleteBatch(List<Long> ids);


    /**
     * 根据Id获取菜品信息（包含口味）
     * @param id 菜品id
     * @return 菜品VO
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息（包含口味）
     * @param dishDTO 菜品DTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根据条件查询菜品和口味
     * @param dish 查询条件
     * @return 菜品和口味列表
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 菜品的起售和停售
     * @param status 待修改的状态
     * @param id 菜品的id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类id
     * @return 菜品列表信息
     */
    List<Dish> list(Long categoryId);

    interface SetmealService {
    }
}
