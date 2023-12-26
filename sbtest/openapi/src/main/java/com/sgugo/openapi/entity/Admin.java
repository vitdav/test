package com.sgugo.openapi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;

@Data
public class Admin {
    @ApiModelProperty(value="用户名",required=true,example="Aaron")
    private String username;

    @ApiModelProperty(value="密码",required=true,example="123456")
    private String password;

    @ApiModelProperty(value="年龄",required=false,example="20")
    private Integer age;
}
