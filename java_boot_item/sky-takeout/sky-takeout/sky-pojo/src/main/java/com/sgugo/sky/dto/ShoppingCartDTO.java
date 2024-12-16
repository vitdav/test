package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="ShoppingCartDTO-购物车添加的DTO")
public class ShoppingCartDTO implements Serializable {

    @Schema(description = "菜品id",defaultValue = "1")
    private Long dishId;

    @Schema(description = "套餐id",defaultValue = "11")
    private Long setmealId;

    @Schema(description = "口味信息",defaultValue = "1")
    private String dishFlavor;

}
