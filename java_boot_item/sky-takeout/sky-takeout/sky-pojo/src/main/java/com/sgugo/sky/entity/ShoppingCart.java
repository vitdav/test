package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="ShoppingCart-购物车实体类")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id", defaultValue = "1")
    private Long id;

    @Schema(description = "商品名称", defaultValue = "酸菜鱼")
    private String name;

    @Schema(description = "用于id,逻辑外键", defaultValue = "1")
    private Long userId;

    @Schema(description = "菜品id,逻辑外键", defaultValue = "1")
    private Long dishId;

    @Schema(description = "套餐id,逻辑外键", defaultValue = "11")
    private Long setmealId;

    @Schema(description = "口味信息")
    private String dishFlavor;

    @Schema(description = "商品数量", defaultValue = "1")
    private Integer number;

    @Schema(description = "商品单价", defaultValue = "10")
    private BigDecimal amount;

    @Schema(description = "图片", defaultValue = "a.jpg")
    private String image;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
