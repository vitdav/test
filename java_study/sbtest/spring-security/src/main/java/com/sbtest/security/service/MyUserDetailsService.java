package com.sbtest.security.service;

import com.sbtest.security.entity.Role;
import com.sbtest.security.entity.User;
import com.sbtest.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

    private final UserMapper userMapper;

    @Autowired
    public MyUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
        //1.查询用户
        User user = userMapper.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user))
            throw new UsernameNotFoundException("用户不存在");
        //2.查询权限信息
        List<Role> roles = userMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }


    /**
     * 重写UserDetailsPasswordService接口的方法，更新用户密码
     * 默认使用bcrypt进行密码加密
     * @param user UserDetails对象
     * @param newPassword 新密码
     * @return UserDetails对象
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        //调用Mapper更新密码
        Integer result = userMapper.updatePassword(user.getUsername(), newPassword);
        if(result == 1){
            //受影响行数==1，表示更新成功，此时同步更新内存中的密码
            ((User)user).setPassword(newPassword);
        }
        return user;
    }
}
