package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DishFlavor：菜品口味的实体类")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键",defaultValue = "1")
    private Long id;

    //菜品id
    @Schema(description = "口味所属的菜品id",defaultValue = "1")
    private Long dishId;

    //口味名称
    @Schema(description = "口味名称",defaultValue = "忌口")
    private String name;

    //口味数据list
    @Schema(description = "口味值",defaultValue = "[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]")
    private String value;

}
