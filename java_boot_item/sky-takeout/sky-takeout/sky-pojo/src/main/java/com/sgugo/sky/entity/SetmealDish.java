package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="SetmealDish：套餐菜品关系表")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键", defaultValue = "1")
    private Long id;

    @Schema(description = "套餐id", defaultValue = "1")
    private Long setmealId;

    @Schema(description = "菜品id", defaultValue = "1")
    private Long dishId;

    @Schema(description = "菜品名", defaultValue = "小龙虾")
    private String name;

    @Schema(description = "菜品单价", defaultValue = "10.5")
    private BigDecimal price;

    @Schema(description = "菜品份数", defaultValue = "1")
    private Integer copies;
}
