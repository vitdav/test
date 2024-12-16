package com.sgugo.sky.controller.user;

import com.sgugo.sky.entity.Category;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Category-商品分类")
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    @Operation(summary = "list->查询分类")
    @Parameter(name="type",description = "分类id：1菜品 2套餐", example = "1")
    public R<List<Category>> list(@Nullable Integer type){
        List<Category> list = categoryService.list(type);
        return R.success(list);
    }
}
