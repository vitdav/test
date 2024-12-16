package com.sgugo.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="UserLoginVO：用户登录响应信息")
public class UserLoginVO implements Serializable {

    //用户id：数据库中储存的id
    @Schema(description = "用户id",required = true)
    private Long id;

    //微信服务器返回的openid
    @Schema(description = "微信openid",required = true)
    private String openid;

    //JWT令牌
    @Schema(description = "JWT Token",required = true)
    private String token;
}
