package com.sgugo.sky.mapper;

import com.sgugo.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据openid 查询用户
     * @param openid openid
     * @return User对象
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 微信用户首次登录时，进行注册，将用户数据插入
     * @param user User对象
     */
    void insert(User user);


    /**
     * 根据用户id，查询用户数据
     * @param userId 用户id
     * @return 用户数据
     */
    @Select("select * from user where id = #{id}")
    User getById(Long userId);
}
