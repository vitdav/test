package com.sbtest.security.mapper;

import com.sbtest.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名返回用户信息
     * @param UserName 用户名
     * @return 用户的所有信息
     */
    User getByUserName(String UserName);

}
