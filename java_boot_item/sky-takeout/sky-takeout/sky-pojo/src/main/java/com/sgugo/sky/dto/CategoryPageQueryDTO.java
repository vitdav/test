package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description="分类数据分页查询的DTO")
public class CategoryPageQueryDTO implements Serializable {

    //查询第几页
    @Schema(description="查询的页码", defaultValue = "1", required=true)
    private int page;

    //每页显示几条数据
    @Schema(description="每页记录数", defaultValue = "10", required = true)
    private int pageSize;

    //可根据分类名称查询
    @Schema(description="分类名称",defaultValue = "传统主食")
    private String name;

    //可选择查询分类的类型
    @Schema(description="分类类型：1菜品 2套餐", defaultValue = "1")
    private Integer type;

}
