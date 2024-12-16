package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="Setmeal：套餐表实体类")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "自增主键",defaultValue = "1")
    private Long id;

    //分类id
    @Schema(description = "套餐分类，如人气套餐",defaultValue = "1")
    private Long categoryId;

    //套餐名称
    @Schema(description="套餐名",defaultValue = "单人随心想套餐")
    private String name;

    //套餐价格
    @Schema(description="套餐价格",defaultValue = "25")
    private BigDecimal price;

    //状态 0:停用 1:启用
    @Schema(description="状态， 0:停用 1:启用",defaultValue = "1")
    private Integer status;

    //描述信息
    @Schema(description = "描述信息", defaultValue = "限时套餐，快来抢")
    private String description;

    //图片
    @Schema(description = "图片", defaultValue = "05c7bb83-1139-4127-83c2-b8309e05ce16.jpg")
    private String image;


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

