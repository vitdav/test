package com.sbtest.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbtest.security.filter.LoginFilter;
import com.sbtest.security.service.MyRememberMeServices;
import com.sbtest.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled=true,
        securedEnabled=true,
        jsr250Enabled=true
)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
    //1. 注入自定义的数据源 DetailsService
    private final MyUserDetailsService myUserDetailsService;
    private final DataSource dataSource;


    @Autowired
    public SecurityConfigure(MyUserDetailsService myUserDetailsService, DataSource dataSource) {
        this.myUserDetailsService = myUserDetailsService;
        this.dataSource = dataSource;
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
        //设置认证成功时使用的自定义rememberMeService
        loginFilter.setRememberMeServices(rememberMeServices());

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

    //2.指定数据库持久化
    // @Bean
    public PersistentTokenRepository jdbcToken(){
        //基于数据库实现，使用JdbcTokenRepository替代默认的内存实现
        JdbcTokenRepositoryImpl jdbcToken = new JdbcTokenRepositoryImpl();
        //指定数据源
        jdbcToken.setDataSource(dataSource);
        //创建表结构，第一次新建表结构时设置为true，第二次后要手动改为false
        jdbcToken.setCreateTableOnStartup(false);
        return jdbcToken;
    }


    //5. 自定义RememberMeServices记住我实现
    public RememberMeServices rememberMeServices(){
        return new MyRememberMeServices(
                UUID.randomUUID().toString(),
                userDetailsService(),
                jdbcToken()
        );
    }


    //6. 配置CORS 跨域
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许的请求头：* 表示所有
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        // 允许的请求方法：*表示所有
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        // 允许的请求源：*表示任何源
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        // 设置响应时间
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //允许跨域访问的路径：*表示所有路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            //1. 开启请求的权限认证
            authorizeHttpRequests()
                //1.1 放行 验证码和Login
                .mvcMatchers("/vc","login").permitAll()
                //1.2 放行静态资源
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                //1.3 放行API文档
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                //1.4 拦截其他所有请求
                .anyRequest().authenticated()

            //2. 开启FormLogin登录
            .and().formLogin()

            //3. 配置注销
            .and().logout()// 手动开启注销
                //3.1 手动指定注销的url，默认是 `logout`,且为get请求
                .logoutUrl("/logout")
                //3.2 注销后的处理
                .logoutSuccessHandler(new MyLogoutSuccessHandler())


            //4. 开启Oauth2 认证
            .and().oauth2Login()

            //6. 开启自定义异常处理
            .and().exceptionHandling()
                //6.1 处理认证异常
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                //6.2 处理授权异常
                .accessDeniedHandler(new MyAccessDeniedHandler())
            //7. 跨域处理
            .and().cors()
                //7.1 添加跨域配置
                .configurationSource(corsConfigurationSource())
            //8. CSRF 攻击防御
            .and().csrf()
                //8.1 开启双Token验证
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());



        //扩展过滤器
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}


