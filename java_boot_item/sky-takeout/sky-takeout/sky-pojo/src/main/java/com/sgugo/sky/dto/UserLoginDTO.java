package com.sgugo.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="UserLoginDTO：微信用户登录")
public class UserLoginDTO implements Serializable {
    //微信授权码
    @Schema(description = "微信授权码", required=true)
    private String code;
}
