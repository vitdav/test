package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="EmployeePageQueryDTO：查询员工列表的DTO")
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    @Schema(description = "员工姓名")
    private String name;

    //页码
    @Schema(description = "页码", defaultValue = "1",required=true)
    private int page;

    //每页显示记录数
    @Schema(description = "每页显示记录数", defaultValue = "10", required=true)
    private int pageSize;

}
