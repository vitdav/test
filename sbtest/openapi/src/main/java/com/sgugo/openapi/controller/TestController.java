package com.sgugo.openapi.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="test")
@ApiSupport(author="Victor",order=1)
@RestController("/test")
public class TestController {

    @ApiOperationSupport(author="Aaron")
    @ApiOperation(
        value="test:admin",
        notes="更详细说明：test接口的第一个测试"
    )
    @GetMapping("/test1")
    public void test1(){
        System.out.println("test1");
    }
}
