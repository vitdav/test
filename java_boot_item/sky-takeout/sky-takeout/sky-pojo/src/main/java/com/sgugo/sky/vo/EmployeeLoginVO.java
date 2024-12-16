package com.sgugo.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="EmployeeLoginVO：员工登录的VO")
@AllArgsConstructor
public class EmployeeLoginVO implements Serializable {

    @Schema(description = "员工id",defaultValue = "1")
    private long id;

    @Schema(description = "账号",defaultValue = "admin")
    private String userName;

    @Schema(description = "员工姓名",defaultValue = "管理员")
    private String name;

    @Schema(description = "JWT Token",defaultValue = "aaa.bbb.ccc")
    private String token;
}
