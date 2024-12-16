package com.sbtest.security.filter;

import com.sbtest.security.exception.KaptchaNotMatchException;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KaptchaFilter extends UsernamePasswordAuthenticationFilter {
    public static final String KAPTCHA_KEY = "kaptcha";

    private String kaptcha = KAPTCHA_KEY;
    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        //1.判断是否是post方式
        if (request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //2.从请求中获取验证码
        String verifyCode = request.getParameter(getKaptcha());
        //与session中的验证码进行比较
        String sessionVerifyCode = (String) request.getSession().getAttribute("kaptcha");
        if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(sessionVerifyCode) && verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
            return super.attemptAuthentication(request, response);
        }
        //不满足条件时直接抛出异常
        throw new KaptchaNotMatchException("验证码不匹配");

    }
}
