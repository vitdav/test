package com.sbtest.security.service;

import com.sbtest.security.entity.Role;
import com.sbtest.security.entity.User;
import com.sbtest.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;

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
}
