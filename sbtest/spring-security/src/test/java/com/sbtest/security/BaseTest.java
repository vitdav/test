package com.sbtest.security;

import com.sbtest.security.entity.User;
import com.sbtest.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BaseTest {

    @Test
    public void test1(){
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encode = bcrypt.encode("123");
        System.out.println(encode);
        //$2a$10$FXk.2PMscjYgaIEznAuDmORCLcVclpc/ZaBzQvtSYdp3.tbkD3iji
        //$2a$10$fvbl/7mpSUJDm4pTJeFXveuoleTnjLBZChsuegNSWu4OBuV8zbona
    }

}
