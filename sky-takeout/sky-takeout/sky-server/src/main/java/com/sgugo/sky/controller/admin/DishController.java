package com.sgugo.sky.controller.admin;

import com.sgugo.sky.dto.DishDTO;
import com.sgugo.sky.dto.DishPageQueryDTO;
import com.sgugo.sky.result.R;
import com.sgugo.sky.result.Rs;
import com.sgugo.sky.service.DishService;
import com.sgugo.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Tag(name="03.Dish-菜品管理")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dishDTO DTO
     * @return R
     */
    @PostMapping
    @Operation(summary="save->新增菜品")
    public R save(@RequestBody DishDTO dishDTO){
        dishService.saveWithFlavor(dishDTO);

        return R.success();
    }

    /**
     * 菜品分页查询
     * @param dto 分页查询条件的dto
     * @return 分页查询的结果，Rs是用来返回分页数据的
     */
    @GetMapping("/page")
    @Operation(summary="page->分页查询菜品")
    public R<Rs> page(DishPageQueryDTO dto){
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
    public R delete(@RequestParam List<Long> ids){
        dishService.deleteBatch(ids);
        return R.success();
    }

    /**
     * 根据id查询菜品（包含口味信息）
     * @param id 菜品的id
     * @return 菜品的信息
     */
    @GetMapping("/{id}")
    @Operation(summary="getById->查询菜品")
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
    public R update(@RequestBody DishDTO dishDTO){
        dishService.updateWithFlavor(dishDTO);
        return R.success();
    }
}
