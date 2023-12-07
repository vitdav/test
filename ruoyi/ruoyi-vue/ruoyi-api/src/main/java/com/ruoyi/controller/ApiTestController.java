package com.ruoyi.controller;

import com.ruoyi.common.annotation.Anonymous;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @Anonymous
    @GetMapping("/api/test1")
    public String test1(){
        System.out.println("test-api");
        return "ruoyi-api: test";
    }
}
