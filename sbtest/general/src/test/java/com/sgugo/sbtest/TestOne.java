package com.sgugo.sbtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class TestOne {

    @Test
    void first(){
        Jedis jedis = new Jedis("159.75.225.162", 6379);
        jedis.auth("hello1234");
        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }
    @Test
    void keyTest(){
        Jedis jedis = new Jedis("159.75.225.162", 6379);
        jedis.auth("hello1234");

        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        jedis.set("k1","v1");
        String k1 = jedis.get("k1");
        System.out.println(k1);

        boolean b = jedis.exists("k1");
        System.out.println(b);

        long ttl = jedis.ttl("k1");
        System.out.println(ttl);
    }

    @Test
    void stringTest(){
        Jedis jedis = new Jedis("159.75.225.162", 6379);
        jedis.auth("hello1234");

        jedis.mset("k1","v1","k2","v2");

        List<String> list = jedis.mget("k1", "k2");
        System.out.println(list);
    }


}
