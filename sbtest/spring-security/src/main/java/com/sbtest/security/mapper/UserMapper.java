package com.sbtest.security.mapper;

import com.sbtest.security.entity.Role;
import com.sbtest.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

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

}
