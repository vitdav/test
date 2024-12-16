package com.sgugo.sky.vo;

import com.sgugo.sky.entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name="DishVO：菜品信息VO")
public class DishVO implements Serializable {


    @Schema(description = "自增id", defaultValue = "1", required = true)
    private Long id;

    //菜品名称
    @Schema(description = "菜品名", defaultValue = "鸡蛋汤", required = true)
    private String name;

    //菜品分类id
    @Schema(description = "菜品分类id", defaultValue = "1", required = true)
    private Long categoryId;

    //菜品价格
    @Schema(description = "价格", defaultValue = "20", required = true)
    private BigDecimal price;

    //图片
    @Schema(description = "图片", defaultValue = "a.jpg", required = true)
    private String image;

    //描述信息
    @Schema(description = "描述信息", defaultValue = "很好喝的鸡蛋汤", required = true)
    private String description;

    //0 停售 1 起售
    @Schema(description = "状态：0停售 1起售", defaultValue = "1", required = true)
    private Integer status;


    //更新时间
    @Schema(description = "更新时间",  required = true)
    private LocalDateTime updateTime;

    //分类名称
    @Schema(description = "分类名称", defaultValue = "汤类", required = true)
    private String categoryName;

    //菜品关联的口味
    @Schema(description = "口味信息",  required = true)
    private List<DishFlavor> flavors = new ArrayList<>();
}
