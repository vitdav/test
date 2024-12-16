package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="EmployeeLoginDTO：员工登录DTO")
public class EmployeeLoginDTO implements Serializable {


    @Schema(description = "账号",defaultValue = "admin",required=true)
    private String username;

    @Schema(description = "密码",defaultValue = "123456",required=true)
    private String password;

}
