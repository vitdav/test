package com.sgugo.sky.mapper;

import com.github.pagehelper.Page;
import com.sgugo.sky.annotation.AutoFill;
import com.sgugo.sky.dto.SetmealPageQueryDTO;
import com.sgugo.sky.entity.Setmeal;
import com.sgugo.sky.enumeration.OperationType;
import com.sgugo.sky.vo.DishItemVO;
import com.sgugo.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 新增套餐
     * @param setmeal 套餐表实体类
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 根据动态条件(分类id)查询套餐信息
     * @param setmeal 查询套餐的动态条件
     * @return 查询到的套餐列表
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id,查询菜品列表信息
     * @param setmealId 套餐id
     * @return 菜品列表信息
     */
    @Select("select sd.name, sd.copies,d.image,d.description" +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id" +
            "where sd.setmeal_id = #{setmealId")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据id修改套餐
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 分页查询
     * @param setmealPageQueryDTO 查询条件
     * @return VO
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id 套餐id
     * @return 套餐信息
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 根据套餐id,删除套餐
     * @param setmealId 套餐id
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long setmealId);

    /**
     * 根据套餐id，查询套餐信息和套餐关联的菜品信息
     * @param id 套餐id
     * @return 套餐信息
     */
    SetmealVO getByIdWithDish(Long id);
}
