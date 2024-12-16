package com.sgugo.monitor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    /**
     *
     * @return
     */
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
