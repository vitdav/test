package com.sbtest.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;


@Configuration
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfigure(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean //将自定义的Filter交给IOC
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();

        //1. 这里可以选择手动指定用户名和密码的参数名，让前端传参更灵活
        loginFilter.setUsernameParameter("uname"); //指定接收json用户名的key
        loginFilter.setPasswordParameter("pwd");//指定接收json密码的key

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


    @Override //自定义 AuthenticationManager
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Bean // 将自定义的AuthenticationManager 暴漏出去
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                // .mvcMatchers("/index").permitAll()
                // .mvcMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()

                // .loginPage("/login") //自定义默认的登录页面，同时必须指定登录的url
                // .loginProcessingUrl("/dologin") //指定处理登录请求的url
                // .successHandler(new MyAuthenticationSuccessHandler())
                // .failureHandler(new MyAuthenticationFailureHandler())
                //推出还是在这里进行配置
                .and().logout()// 手动开启注销
                .logoutUrl("/logout") // 手动指定注销的url，默认是 `logout`,且为get请求

                // .invalidateHttpSession(true) //退出时，是否使session失效，默认是true
                // .logoutSuccessUrl("/login")//退出成功后跳转的页面
                // .clearAuthentication(false)
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().csrf().disable();

        //扩展过滤器
        // 1.addFilterAtt：将一个Filter，替换过滤器链中的某个Filter
        // 2.after：将一个Filter，放在过滤器链中某个Filter之前
        // 2.before：将一个Filter，放在过滤器链中某个Filter之后
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}


