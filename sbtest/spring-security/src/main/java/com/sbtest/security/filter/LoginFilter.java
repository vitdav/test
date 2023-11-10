package com.sbtest.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbtest.security.exception.KaptchaNotMatchException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    //1. 指定储存kaptcha验证码文本的 Session Key
    public static final String KAPTCHA_KEY = "kaptcha";

    private String kaptchaKey = KAPTCHA_KEY;

    public String getKaptchaKey(){
        return kaptchaKey;
    }
    public void setKaptchaKey(String kaptchaKey) {
        this.kaptchaKey = kaptchaKey;
    }


    //2. 重写attemptAuthentication方法，获取请求参数
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        //2.1 判断是否是post方式
        if (!request.getMethod().equals("POST")) {

            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        }

        //2.2 从请求参数中获取请求数据
        try {
            Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);

            // 2.2.1 获取json中的验证码
            String kaptcha = userInfo.get(getKaptchaKey());
            // 2.2.2 获取json中的用户名
            String username = userInfo.get(getUsernameParameter());
            // 2.2.3 获取json中的密码
            String password = userInfo.get(getPasswordParameter());
            // 2.2.4 获取json中的remember-me
            String rememberMe = userInfo.get(AbstractRememberMeServices.DEFAULT_PARAMETER);
        //2.3 判断remember-me参数是否未空
            if(!ObjectUtils.isEmpty(rememberMe)){
                //添加一个请求参数，值为rememberMe(true或false)
                request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER,rememberMe);
            }

        //2.4 校验验证码，验证码校验通过再校验密码，防止无效查询数据库
            //返回验证码的时候，已经在session存了一份，直接获取并对比即可
            String code = (String) request.getSession().getAttribute("kaptcha");
            System.out.println("session中的验证码："+code);
            System.out.println("传过来的验证码："+kaptcha);
            if (!(!ObjectUtils.isEmpty(kaptcha) && !ObjectUtils.isEmpty(code) && kaptcha.equalsIgnoreCase(code))) {
                throw new KaptchaNotMatchException("验证码不匹配");
            }

        //2.5 校验用户名和密码
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.attemptAuthentication(request, response);
    }
}
