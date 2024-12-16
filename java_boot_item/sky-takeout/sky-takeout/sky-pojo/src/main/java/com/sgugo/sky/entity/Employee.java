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
//@Builder
@Schema(name="Employee：员工表实体类")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键",defaultValue = "1")
    private Long id;

    @Schema(description = "账户",defaultValue = "admin")
    private String username;

    @Schema(description = "姓名",defaultValue = "财务")
    private String name;

    @Schema(description = "密码",defaultValue = "123456")
    private String password;

    @Schema(description = "手机号",defaultValue = "13812345678")
    private String phone;

    @Schema(description = "性别：1=男，0=女",defaultValue = "1")
    private String sex;

    @Schema(description = "身份证",defaultValue = "110101199001010047")
    private String idNumber;

    @Schema(description = "用户状态：1正常 0锁定",defaultValue = "1")
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
