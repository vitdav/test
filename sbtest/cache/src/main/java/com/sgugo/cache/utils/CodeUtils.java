package com.sgugo.cache.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static java.lang.Math.floor;

@Component
public class CodeUtils {

    //用一种算法，为不同的手机号模拟出不同的验证码，这里直接返回4位随机数
    public String  generator(String tele){
        int code = (int)floor(Math.random() * 10000);
        return String.valueOf(code);
    }

    //获取缓存中的验证码，有就返回，没有就返回null
    @Cacheable(value="smsCode",key="#tele")
    public String get(String tele){
        return null;
    }

}



