package com.sbtest.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //自定义认证成功的信息，然后通过 response传递给前端
        HashMap<String, Object> result = new HashMap<>();
        // result.put("status",200); //缓存Spring内置的状态码枚举
        result.put("msg","登录成功");
        //获取用户信息，也一并返回
        User userInfo = (User) authentication.getPrincipal();
        // result.put("authentication", authentication); //获取权限信息返回，可选
        result.put("userInfo",userInfo);


        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        String s = new ObjectMapper().writeValueAsString(result);

        response.getWriter().println(s);
    }
}
