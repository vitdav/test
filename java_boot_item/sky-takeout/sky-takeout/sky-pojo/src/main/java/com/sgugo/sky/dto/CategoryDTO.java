package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="CategoryDTO：分类信息DTO")
public class CategoryDTO implements Serializable {

    //分类ID：已设置为自增，不给值，或值为0，都会自增
    @Schema(description="自增id",defaultValue = "1")
    private Long id;

    //类型: 1 菜品分类 2 套餐分类
    @Schema(description="分类类型：1=菜品，2=套餐", defaultValue = "1",required=true)
    private Integer type;

    //分类名称
    @Schema(description="分类名称", defaultValue = "主食",required=true)
    private String name;

    //排序
    @Schema(description="分类的排序，升序",defaultValue = "1",required = true)
    private Integer sort;

}
