package com.sgugo.sky.controller.user;

import com.sgugo.sky.constant.JwtClaimsConstant;
import com.sgugo.sky.dto.UserLoginDTO;
import com.sgugo.sky.entity.User;
import com.sgugo.sky.properties.JwtProperties;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.UserService;
import com.sgugo.sky.utils.JwtUtil;
import com.sgugo.sky.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Tag(name="UserController-用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 微信用户登录
     * @param userLoginDTO DTO
     * @return VO
     */
    @PostMapping("/login")
    @Operation(summary="login->微信登录")
    public R<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信用户登录：{}",userLoginDTO.getCode());

        //调用service处理微信登录，登陆成功后，用户信息会被封装到User对象
        User user = userService.wxLogin(userLoginDTO);

        //登陆成功后，生成JWT令牌发回给前端
        Map<String, Object> claims = new HashMap<>();
        //将用户的id封装到JWT中
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        //创建Token
        String token = JwtUtil.createJWT(jwtProperties.getUserTtl(),jwtProperties.getUserSecret(),claims);

        //设置用户需要返回的信息，用户信息从User对象中获取
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        System.out.println("微信用户的Token，用于测试@@："+token);
        return R.success(userLoginVO);
    }
}
