package com.sbtest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
//添加security过滤器，此处一定要加入此注解，否则下面的httpSecurity无法装配
@EnableWebSecurity
public class SecurityConfigure{

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests()
                .mvcMatchers("index").permitAll()
                // .mvcMatchers("/login.html").permitAll()
                .mvcMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login") //自定义默认的登录页面，同时必须指定登录的url
                .loginProcessingUrl("/dologin") //指定处理登录请求的url
                .successHandler(new MyAuthenticationSuccessHandler())
                // .failureUrl("/login") //认证失败之后 redirect跳转
                // .failureForwardUrl("/login") //认证失败之后 forward跳转
                .failureHandler(new MyAuthenticationFailureHandler())

                .and().logout()// 手动开启注销
                // .logoutUrl("/logout") // 手动指定注销的url，默认是 `logout`,且为get请求
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET"),
                        new AntPathRequestMatcher("/bbb", "POST")
                ))
                .invalidateHttpSession(true) //退出时，是否使session失效，默认是true
                .logoutSuccessUrl("/login")//退出成功后跳转的页面
                .clearAuthentication(false)
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().csrf().disable()
                .build();
    }
}


