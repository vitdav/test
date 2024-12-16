package com.sgugo.sky.mapper;

import com.github.pagehelper.Page;
import com.sgugo.sky.annotation.AutoFill;
import com.sgugo.sky.dto.DishPageQueryDTO;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.enumeration.OperationType;
import com.sgugo.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据主键查询菜品信息
     * @param id id
     * @return Dish实体类
     */
    @Select("select * from dish WHERE id = #{id}")
    Dish getById(Long id);

    /**
     * 查询分类下关联的菜品数量
     * @param id 分类id
     * @return 菜品数量
     */
    @Select("select count(id) from dish where category_id = #{id}")
    Integer  countByCategoryId(Long id);

    /**
     * 新增菜品
     * @param dish 菜品实体类
     */
    @AutoFill(value= OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     * @param dto dto
     * @return 分页数据
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dto);

    /**
     * 根据id删除菜品
     * @param id 菜品id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 更新菜品信息
     * @param dish 菜品实体类
     */
    @AutoFill(value=OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据动态条件查询菜品
     * @param dish 查询条件
     * @return 菜品列表
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id,获取套餐内的菜品信息
     * @param setmealId 套餐id
     * @return 菜品列表信息
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id " +
            "where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
}
