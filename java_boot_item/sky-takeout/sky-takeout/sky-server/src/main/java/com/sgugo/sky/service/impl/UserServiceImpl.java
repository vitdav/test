package com.sgugo.sky.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sgugo.sky.constant.MessageConstant;
import com.sgugo.sky.dto.UserLoginDTO;
import com.sgugo.sky.entity.User;
import com.sgugo.sky.exception.LoginFailedException;
import com.sgugo.sky.mapper.UserMapper;
import com.sgugo.sky.properties.WeChatProperties;
import com.sgugo.sky.service.UserService;
import com.sgugo.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址：用于用户登录
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    //微信配置类
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //1. 使用授权码，获取用户的openid
        String openid = getOpenid(userLoginDTO.getCode());

        //2. 对openid的结果进行判断
        //2.1 判断openid是否为空，如果为空表示登录失败，抛出业务异常
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //2.2 判断当前用户是否是首次登录，是的话就进行注册，并返回用户信息
        User user = userMapper.getByOpenid(openid);
        if(user == null){
            //是新用户，进行注册，将用户信息保存到数据库
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();

            userMapper.insert(user);
        }
        return user;
    }


    /**
     * 调用微信接口服务，获取微信用户的openid
     * @param code 微信授权码
     * @return openid
     */
    private String getOpenid(String code){
        //掉用微信接口服务，获得当前微信用户的openid，进行用户登录
        HashMap<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        //使用HttpClient发送请求，获取返回结果
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        //从返回结果里提取appid（用户的唯一标识），其他数据根据业务决定是否保留，如用户头像
        String openid = jsonObject.getString("openid");

        return openid;
    }
}
