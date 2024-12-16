package com.sgugo.sky.mapper;

import com.github.pagehelper.Page;
import com.sgugo.sky.annotation.AutoFill;
import com.sgugo.sky.dto.CategoryPageQueryDTO;
import com.sgugo.sky.entity.Category;
import com.sgugo.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {


    /**
     * 添加分类
     * @param category PO
     */
    @AutoFill(OperationType.INSERT)
    void insert(Category category);


    /**
     * 菜品分类的分页查询
     * @param categoryPageQueryDTO DTO
     * @return 分页查询的结果
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据ID 删除分类
     * @param id 待删除的分类id
     */
    @Delete("delete from category where id= #{id}")
    void deleteById(Long id);


    /**
     * 更新分类
     * @param category 实体类
     */
    void update(Category category);

    /**
     * 据类型（套餐or菜品）查询分类
     * @param type 餐或菜品（1=菜品分类，2=套餐分类）
     * @return 分类列表
     */
    List<Category> list(Integer type);
}
