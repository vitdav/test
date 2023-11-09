package com.sbtest.security.mapper;

import com.sbtest.security.entity.Role;
import com.sbtest.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User loadUserByUsername(String username);

    /**
     * 根据用户id查询一个角色，注意一个用户可能不止一种角色
     * @param uid
     * @return
     */
    List<Role> getRolesByUid(Integer uid);

    /**
     * 根据用户名更新密码
     * @param username
     * @param password
     * @return
     */
    Integer updatePassword(@Param("username") String username,@Param("password") String password);
}
