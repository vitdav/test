package com.sbtest.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;

public class MyRememberMeServices extends PersistentTokenBasedRememberMeServices {

    //必须调用父类的构造方法
    public MyRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    // 自定义前后端分离获取remember-me方式
    // parameter参数就是`remember-me`参数的名字
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {

        //获取请求参数中的remember-me参数的值（true或false)
        String paramValue = request.getAttribute(parameter).toString();
        if(parameter != null){
            if(paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on") || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")){
                return true;
            }
        }
        return false;
    }
}
