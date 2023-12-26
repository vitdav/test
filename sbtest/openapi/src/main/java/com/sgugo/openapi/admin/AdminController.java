package com.sgugo.openapi.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.sgugo.openapi.entity.Admin;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="admin")
@ApiSupport(author="Victor",order=1)
@RestController("/admin")
public class AdminController {

    @PostMapping({"/setAdmin"})
    @ApiResponses({
            @ApiResponse(code=101,message="用户已被冻结")
    })
    public Admin setAdmin(@RequestBody Admin admin){
        return admin;
    }

}
