package com.sbtest.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class AuthorizeMethodController {

    // @PreAuthorize：既可以验证角色，又可以验证权限字符串 支持and、or等连接符
    @PreAuthorize(
        //验证具有ADMIN角色，且用户名是root
        // "hasRole('ADMIN') and authentication.name == 'root'",
         // 验证READ_INFO权限
        // "hasAuthority('READ_INFO')"
        // "hasRole('ADMIN')"
        "authentication.name == 'root'"
    )
    public String hello(){
        return "hello";
    }
}
