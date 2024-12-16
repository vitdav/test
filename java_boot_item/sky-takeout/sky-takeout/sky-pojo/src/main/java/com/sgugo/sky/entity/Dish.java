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
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Dish：菜品表实体类")
@Builder
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键",defaultValue = "1")
    private Long id;

    //菜品名称
    @Schema(description = "菜品名称，唯一",defaultValue = "烤鱼")
    private String name;

    //菜品分类id
    @Schema(description="分类id", defaultValue = "1")
    private Long categoryId;

    //菜品价格
    @Schema(description = "价格", defaultValue = "12.2")
    private BigDecimal price;

    //图片
    @Schema(description="图片路径",defaultValue = "05c7bb83-1139-4127-83c2-b8309e05ce16.jpg")
    private String image;

    //描述信息
    @Schema(description = "描述信息",defaultValue = "正宗烤鱼，好味道")
    private String description;

    //0 停售 1 起售
    @Schema(description = "售卖状态：1=起售，0=停售",defaultValue = "1")
    private Integer status;

    //创建时间
    @Schema(description="创建时间")
    private LocalDateTime createTime;

    //更新时间
    @Schema(description="更新时间")
    private LocalDateTime updateTime;

    //创建人
    @Schema(description="创建人")
    private Long createUser;

    //修改人
    @Schema(description="修改人")
    private Long updateUser;
}