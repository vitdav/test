package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="EmployeeDTO：员工信息DTO")
public class EmployeeDTO implements Serializable {

    @Schema(description = "员工id",defaultValue = "1")
    private Long id;

    @Schema(description = "账号",defaultValue = "admin")
    private String username;

    @Schema(description = "员工id",defaultValue = "1")
    private String name;

    @Schema(description = "手机号",defaultValue = "15212345611")
    private String phone;

    @Schema(description = "性别:1男 0女",defaultValue = "1")
    private String sex;

    @Schema(description = "身份证",defaultValue = "130424199503115611")
    private String idNumber;
}
