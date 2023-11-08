package com.sbtest.security.config;

import com.sbtest.security.filter.LoginFilter;
import com.sbtest.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
    //1. 注入自定义的数据源 DetailsService
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public SecurityConfigure(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    //2.自定义 AuthenticationManager，覆盖默认的
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(myUserDetailsService);
    }

    //3. 将自定义的AuthenticationManager 暴漏出去
    @Bean // 将自定义的AuthenticationManager 暴漏出去
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // 4. 将自定义的Filter交给IOC
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();

        //1. 这里可以选择手动指定用户名、密码和验证码的参数名，让前端传参更灵活
        loginFilter.setUsernameParameter("uname"); //指定接收json用户名的key
        loginFilter.setPasswordParameter("pwd");//指定接收json密码的key
        loginFilter.setKaptchaKey("kaptcha");

        //2. 注入自定义的AuthenticationManager
            //调用暴漏自定义AuthenticationManager的方法，进行获取
        loginFilter.setAuthenticationManager(authenticationManager());

        // 3. 指定验证成功和失败的Handler，
        //替代的是 HttpSecurity的successHandler和failureHandler配置
            //3.1 认证成功的处理
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
            //3.2 认证失败的处理
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());

        //4. 指定认证的URL
        loginFilter.setFilterProcessesUrl("/doLogin");

        return loginFilter;
    }

    //创建一个自定义的密码加密器
    @Bean
    public PasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .mvcMatchers("/vc").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())

                //注销还是在这里进行配置
                .and().logout()// 手动开启注销
                .logoutUrl("/logout") // 手动指定注销的url，默认是 `logout`,且为get请求
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().csrf().disable();

        //扩展过滤器
        // 1.addFilterAtt：将一个Filter，替换过滤器链中的某个Filter
        // 2.before：将一个Filter，放在过滤器链中某个Filter之前
        // 2.after：将一个Filter，放在过滤器链中某个Filter之后
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);


    }
}


