package com.sbtest.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginFilterOld extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        //1.判断是否是POST请求，仅支持POST
        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("method not supported");
        }

        //2.获取用户数据
            //2.1 获取之前,判断数据格式是否为JSON
        if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            //2.2 从JSON数据中获取用户名和密码，格式：{"username":"xxx",password:"xxx"}
            try {
                //这里使用了jackson里的API
                Map<String,String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                //2.3 获取用户名和密码
                //PS：参数名是在父类里通过属性写死的，可以通过getter方法获得
                //PS：也可以考虑在创建实例时注入属性替换父类的属性，使用更灵活
                String username = userInfo.get(getUsernameParameter());
                String password = userInfo.get(getPasswordParameter());
                //2.4 将用户名密码进行封装
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request,token);

                //3. 返回Authentication
                return this.getAuthenticationManager().authenticate(token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 2.5 若请求的方式和数据格式不满足，直接走原始的通道
        return super.attemptAuthentication(request,response);

    }
}
