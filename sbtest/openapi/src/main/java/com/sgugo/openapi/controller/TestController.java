package com.sgugo.openapi.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@Api(tags="test",hidden=true)
@ApiSupport(author="Victor",order=1)
@RestController("/test")
public class TestController {

    @ApiOperationSupport(author="Aaron")
    // @ApiOperation(
    //     value="test:admin"
    //     // notes="更详细说明：test接口的第一个测试"
    // )
    @PostMapping("/sayHi")
    // @ApiIgnore
    @ApiImplicitParams({
        @ApiImplicitParam(name="str",value="问候语",required=true,example="hi"),
        @ApiImplicitParam(name="age",value="年龄",required=false,dataType="int",example="10")
    })
    public void sayHi(String str, @ApiIgnore Integer age){
        System.out.println(str);
    }
}
