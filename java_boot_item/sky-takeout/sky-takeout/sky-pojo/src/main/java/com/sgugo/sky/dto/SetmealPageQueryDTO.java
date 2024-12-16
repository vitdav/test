package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="SetmealPageQueryDTO-套餐查询")
public class SetmealPageQueryDTO implements Serializable {

    @Schema(description = "查询的页数",defaultValue = "1")
    private int page;

    @Schema(description = "每页显示的条数",defaultValue = "10")
    private int pageSize;

    @Schema(description = "套餐名称",defaultValue = "一人食烤鱼")
    private String name;

    @Schema(description = "套餐分类", defaultValue = "商务套餐")
    private Integer categoryId;

    @Schema(description = "套餐状态：1起售 0停售", defaultValue = "1")
    private Integer status;

}
