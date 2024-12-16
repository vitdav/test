package com.sgugo.sky.vo;

import com.sgugo.sky.entity.SetmealDish;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name="SetmealVO-套餐查询结果")
public class SetmealVO {

    @Schema(description = "自增主键", defaultValue = "1")
    private Long id;

    @Schema(description = "分类id",defaultValue = "12")
    private Long categoryId;

    @Schema(description = "套餐名称", defaultValue = "一人食烤鱼套餐")
    private String name;

    @Schema(description = "套餐价格", defaultValue = "50")
    private BigDecimal price;

    @Schema(description = "套餐状态：0停售 1启售", defaultValue = "1")
    private Integer status;

    @Schema(description = "描述信息", defaultValue = "烤鱼好好吃")
    private String description;

    @Schema(description = "图片", defaultValue = "a.jpg")
    private String image;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "分类名称", defaultValue = "人气套餐")
    private String categoryName;

    @Schema(description = "套餐和菜品关联关系")
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
