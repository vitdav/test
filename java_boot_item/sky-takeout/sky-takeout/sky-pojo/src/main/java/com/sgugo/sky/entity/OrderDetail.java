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
@Schema(name="OrderDetail：订单详情表实体类")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键",defaultValue = "1")
    private Long id;

    @Schema(description = "商品名称",defaultValue = "烤鱼")
    private String name;

    @Schema(description = "订单id，订单表的逻辑外键",defaultValue = "1")
    private Long orderId;

    @Schema(description = "菜品id",defaultValue = "1")
    private Long dishId;

    @Schema(description = "套餐id",defaultValue = "1")
    private Long setmealId;

    @Schema(description = "口味信息")
    private String dishFlavor;

    @Schema(description = "数量",defaultValue = "2")
    private Integer number;

    @Schema(description = "商品单价",defaultValue = "30")
    private BigDecimal amount;

    @Schema(description = "图片",defaultValue = "1.jpg")
    private String image;
}
