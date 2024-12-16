package com.sgugo.sky.controller.admin;

import com.sgugo.sky.dto.SetmealDTO;
import com.sgugo.sky.dto.SetmealPageQueryDTO;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.SetmealService;
import com.sgugo.sky.vo.PageResultVO;
import com.sgugo.sky.vo.SetmealVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Tag(name="SetmealController-套餐管理")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO DTO
     * @return 是否新增成功
     */
    @PostMapping
    @Operation(summary = "save->新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key="#setmealDTO.categoryId")
    public R save(@RequestBody SetmealDTO setmealDTO){
        setmealService.saveWithDish(setmealDTO);
        return R.success();
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO 查询条件
     * @return 查询结果
     */
    @GetMapping("/page")
    @Operation(summary = "page->套餐分页查询")
    public R<PageResultVO> page(@ParameterObject  SetmealPageQueryDTO setmealPageQueryDTO){
        PageResultVO pageResultVO = setmealService.pageQuery(setmealPageQueryDTO);
        return R.success(pageResultVO);
    }


    /**
     * 删除套餐，可批量删除
     * @param ids 待删除的id列表
     * @return 删除是否成功
     */
    @DeleteMapping
    @Operation(summary = "delete->删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public R delete (@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return R.success();
    }

    /**
     * 根据套餐id，查询套餐信息和套餐关联的菜品信息
     * @param id 套餐id
     * @return 套餐信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "getById->查询套餐信息")
    public R<SetmealVO> getById(@PathVariable Long id){
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return R.success(setmealVO);
    }

    /**
     * 修改套餐
     * @param setmealDTO 修改后的套餐信息
     * @return 修改是否成功
     */
    @PutMapping
    @Operation(summary = "update->修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public R update(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return R.success();
    }

    /**
     * 修改套餐状态
     * @param status 修改后的状态，1启售 0停售
     * @param id 套餐id
     * @return 修改是否成功
     */
    @PostMapping("/status/{status}")
    @Operation(summary="startOrStop->修改套餐状态")
    @CacheEvict(cacheNames = "setmealCache",allEntries=true)
    public R startOrStop(@PathVariable Integer status,Long id){
        setmealService.startOrStop(status,id);
        return R.success();
    }
}
