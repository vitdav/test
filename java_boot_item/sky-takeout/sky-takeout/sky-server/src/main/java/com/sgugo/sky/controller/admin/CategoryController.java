package com.sgugo.sky.controller.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sgugo.sky.dto.CategoryDTO;
import com.sgugo.sky.dto.CategoryPageQueryDTO;
import com.sgugo.sky.entity.Category;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.CategoryService;
import com.sgugo.sky.vo.PageResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品和套餐分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Tag(name="Category-分类模块")
@Log4j2
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO DTO
     * @return 是否添加成功
     */
    @PostMapping
    @Operation(summary="save->新增分类")
    @ApiOperationSupport(order=1)
    public R<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return R.success();
    }

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO DTO
     * @return 分页分类的结果VO
     */
    @GetMapping("/page")
    @Operation(summary = "page->分类的分页查询")
    @ApiOperationSupport(order=2)
    public  R<PageResultVO> page(@ParameterObject CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResultVO VO = categoryService.pageQuery(categoryPageQueryDTO);
        return R.success(VO);
    }

    /**
     * 删除分类
     * @param id 分类的id
     * @return 删除是否成功
     */
    @DeleteMapping
    @Operation(summary = "deleteById->删除分类")
    @ApiOperationSupport(order=3)
    public R<String> deleteById(Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return R.success();
    }

    /**
     * 更新分类信息
     * @param categoryDTO  DTO
     * @return 更新是否成功
     */
    @PutMapping
    @Operation(summary = "update->修改分类")
    @ApiOperationSupport(order=4)
    public R<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return R.success();
    }

    /**
     * 启用禁用分类
     * @param status 分类的状态：0禁用 1启用
     * @param id 分类id
     * @return 处理是否成功
     */
    @PostMapping("/status/{status}")
    @Operation(summary="startOrStop->禁用分类")
    @ApiOperationSupport(order=5)
    public R<String> startOrStop(@PathVariable("status") Integer status,Long id){
        categoryService.startOrStop(status,id);
        return R.success();
    }

    /**
     * 根据类型（套餐or菜品）查询分类
     * @param type 套餐或菜品（1=菜品分类，2=套餐分类）
     * @return 分类列表
     */
    @GetMapping("/list")
    @Operation(summary = "list->根据类型查询分类")
    @ApiOperationSupport(order=6)
    public R<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return R.success(list);
    }
}
