package com.sbtest.security;

import com.sbtest.security.entity.User;
import com.sbtest.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {

    @Autowired
    private UserMapper userMapper;

}
