package com.sbtest.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // ROLE_ADMIN
    @GetMapping("/admin")
    public String admin(){
        return "admin ok";
    }

    //ROLE_USER
    @GetMapping("/user")
    public String user(){
        return "user ok";
    }

    //READ_INFO
    @GetMapping("/getInfo")
    public String getInfo(){
        return "info ok";
    }
}
