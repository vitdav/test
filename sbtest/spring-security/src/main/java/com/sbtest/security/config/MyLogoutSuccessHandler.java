package com.sbtest.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication //当前认证的对象信息
    ) throws IOException, ServletException {
        //自定义返回的信息
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msg", "注销成功，当前认证对象为：" + authentication);//打印认证信息
        // result.put("status", 200);//打印状态码
        response.setContentType("application/json;charset=UTF-8");//设置响应类型
        response.setStatus(HttpStatus.OK.value());
        String s = new ObjectMapper().writeValueAsString(result);//json格式转字符串
        response.getWriter().println(s);//打印json格式数据

    }
}
