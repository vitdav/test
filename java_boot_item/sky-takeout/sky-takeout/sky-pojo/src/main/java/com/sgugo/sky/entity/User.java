package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="User：用户表实体类")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键",defaultValue = "1")
    private Long id;

    //微信用户唯一标识
    @Schema(description = "微信OpenId",defaultValue = "abcd521515")
    private String openid;

    //姓名
    @Schema(description = "用户名称",defaultValue = "吴彦祖")
    private String name;

    //手机号
    @Schema(description = "手机号",defaultValue = "15200000000")
    private String phone;

    //性别 0 女 1 男
    @Schema(description = "性别：0 女 1 男",defaultValue = "1")
    private String sex;

    //身份证号
    @Schema(description = "身份证号",defaultValue = "110101199001010047")
    private String idNumber;

    //头像
    @Schema(description = "头像",defaultValue = "avatar.jpg")
    private String avatar;

    //注册时间
    @Schema(description = "注册时间")
    private LocalDateTime createTime;
}
