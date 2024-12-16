package com.sgugo.cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Math.floor;

@SpringBootTest
public class CacheBaseTest {

    @Test
    void test1(){
        int num = (int)floor(Math.random()*1000000);
        System.out.println(num+' ');
    }
}
