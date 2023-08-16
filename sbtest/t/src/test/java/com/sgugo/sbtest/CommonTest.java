package com.sgugo.sbtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @Author: Aaron Jinno
 * @Description: 通用测试
 * @Date: 2023/8/7 20:53
 * @Since 1.0
 * @Version: 1.0
 */
@SpringBootTest
public class CommonTest {

    @Test
    public void test1(){
        System.out.println("hello-test1");
        File dir = new File("C:\\Users\\A\\Desktop\\INT");

        File[] files = dir.listFiles();


    }
}
