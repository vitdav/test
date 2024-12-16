package com.sgugo.sky.service;

import com.sgugo.sky.dto.UserLoginDTO;
import com.sgugo.sky.entity.User;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO DTO
     * @return User对象
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
