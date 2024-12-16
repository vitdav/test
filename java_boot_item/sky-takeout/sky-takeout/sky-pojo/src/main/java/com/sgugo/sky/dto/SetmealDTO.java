package com.sgugo.sky.dto;

import com.sgugo.sky.entity.SetmealDish;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name="SetmealDTO：套餐信息")
public class SetmealDTO implements Serializable {

    @Schema(description = "自增主键", defaultValue = "1")
    private Long id;

    @Schema(description = "分类id", defaultValue = "11")
    private Long categoryId;

    @Schema(description = "套餐名称",defaultValue = "最新套餐",required = true)
    private String name;

    @Schema(description = "套餐价格", defaultValue = "30.6")
    private BigDecimal price;

    @Schema(description = "套餐状态：0停用 1启用", defaultValue = "1")
    private Integer status;

    @Schema(description = "套餐描述", defaultValue = "套餐真的很实惠")
    private String description;

    @Schema(description = "图片", defaultValue = "a.jpg")
    private String image;

    @Schema(description = "套餐和菜品的关系")
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
