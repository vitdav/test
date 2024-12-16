package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="DishPageQueryDTO：菜品查询的DTO")
public class DishPageQueryDTO implements Serializable {

    @Schema(description = "页码", defaultValue = "1", required = true)
    private int page;

    @Schema(description = "每页记录数", defaultValue = "10", required = true)
    private int pageSize;

    @Schema(description = "菜品名", defaultValue = "鸡蛋汤")
    private String name;

    @Schema(description = "分类id", defaultValue = "1")
    private Integer categoryId; //分类id

    @Schema(description = "售卖状态：0禁用 1启用", defaultValue = "鸡蛋汤")
    private Integer status; //状态 0表示禁用 1表示启用
}
