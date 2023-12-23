package com.sgugo.openapi.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="admin")
// @ApiSupport(author="Victor",order=1)
@RestController("/admin")
public class AdminController {

    // @ApiOperationSupport(author="Aaron")
    // @ApiOperation(
    //         value="admim：admin1",
    //         notes="更详细说明：admin接口的第一个测试"
    //         // tags="分组2"
    // )
    @ApiOperationSupport
    @GetMapping("/hello")
    public void getHello(){
        System.out.println("Hello");
    }

    @GetMapping("/hi")
    public void getHi(){
        System.out.println("hi");
    }
}
