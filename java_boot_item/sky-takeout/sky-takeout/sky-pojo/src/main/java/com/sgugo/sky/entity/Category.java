package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="Category：分类表实体类")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description="分类id，自增",defaultValue = "1")
    private Long id;

    //类型: 1 菜品分类 2 套餐分类
    @Schema(description="分类类型：1=菜品，2=套餐",defaultValue = "1")
    private Integer type;

    //分类名称
    @Schema(description="分类名称：唯一",defaultValue = "测试菜品")
    private String  name;

    //顺序
    @Schema(description="分类的排序：正序",defaultValue = "1")
    private Integer sort;

    //分类状态 0 标识禁用 1 表示启用
    @Schema(description="分类状态：1=启用，0禁用",defaultValue = "1")
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
