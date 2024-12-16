package com.sgugo.sky.dto;

import com.sgugo.sky.entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name="DishDTO：增改菜品的DTO")
public class DishDTO implements Serializable {

    @Schema(description = "自增主键", defaultValue = "1")
    private Long id;

    //菜品名称
    @Schema(description = "菜品名", defaultValue = "小龙虾", required = true)
    private String name;

    //菜品分类id
    @Schema(description = "菜品分类的Id", defaultValue = "1", required = true)
    private Long categoryId;

    //菜品价格
    @Schema(description = "菜品价格", defaultValue = "20", required = true)
    private BigDecimal price;

    //图片
    @Schema(description = "菜品图片", defaultValue = "a.jpg", required = true)
    private String image;

    //描述信息
    @Schema(description = "描述信息", defaultValue = "很好吃的小龙虾")
    private String description;

    //0 停售 1 起售
    @Schema(description = "状态：1起售 0停售", defaultValue = "1")
    private Integer status;

    //口味
    @Schema(description = "口味信息")
    private List<DishFlavor> flavors = new ArrayList<>();
}
