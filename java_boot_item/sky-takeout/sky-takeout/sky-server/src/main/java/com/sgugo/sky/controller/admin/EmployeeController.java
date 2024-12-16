package com.sgugo.sky.controller.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sgugo.sky.constant.JwtClaimsConstant;
import com.sgugo.sky.dto.EmployeeDTO;
import com.sgugo.sky.dto.EmployeeLoginDTO;
import com.sgugo.sky.dto.EmployeePageQueryDTO;
import com.sgugo.sky.entity.Employee;
import com.sgugo.sky.properties.JwtProperties;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.EmployeeService;
import com.sgugo.sky.utils.JwtUtil;
import com.sgugo.sky.vo.EmployeeLoginVO;
import com.sgugo.sky.vo.PageResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *  员工管理接口
 */
@RestController
@RequestMapping("/admin/employee")
@Log4j2
@Tag(name="Employee-员工模块")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 员工登录接口
     * @param employeeLoginDTO 员工登录的DTO
     * @return 员工登录后的信息
     */
    @PostMapping("/login")
    @Operation(summary = "login->员工登录")
    @ApiOperationSupport(order=1)
    public R<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("员工登录");
        //显示初始密码
        //System.out.println("*******"+EncryptMd5.getMd5Pass("123456"));

        // 1. 调用service进行登录
        Employee employee = employeeService.login(employeeLoginDTO);

        //2. 登录成功后，生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminTtl(),
                jwtProperties.getAdminSecret(),
                claims);

        //3. 填充要响应的VO
        EmployeeLoginVO VO = new EmployeeLoginVO(employee.getId(), employee.getUsername(), employee.getName(), token);

        return R.success(VO);
    }

    /**
     * 员工退出登录
     * @return R
     */
    @PostMapping("/logout")
    @Operation(summary = "logout->员工退出")
    @ApiOperationSupport(order=2)
    public R<String> logout(){
        log.info("员工退出");
        return R.success();
    }

    /**
     * 新增员工
     * @param employeeDTO DTO
     * @return 新增是否成功
     */
    @PostMapping
    @Operation(summary = "save->添加员工")
    @ApiOperationSupport(order=3)
    public R save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工:{}",employeeDTO);
        employeeService.save(employeeDTO);
        return R.success();
    }


    /**
     * 员工分页查询
     * @param employeePageQueryDTO DTO
     * @return R
     */
    @GetMapping("/page")
    @Operation(summary = "page->员工分页查询")
    @ApiOperationSupport(order=4)
    //参数类型是Query而非JSON，因此参数不需要加@RequestBody注解
    public R<PageResultVO> page(@ParameterObject EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询，参数为：{}", employeePageQueryDTO);

        PageResultVO VO = employeeService.pageQuery(employeePageQueryDTO);
        return R.success(VO);
    }

    /**
     * 启用和禁用员工
     * @param status 要修改的员工状态
     * @param id 被修改的员工id
     * @return 操作是否成功
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "startOrStop->禁用员工账号")
    @ApiOperationSupport(order=5)
    public R startOrStop(@PathVariable Integer status,Long id){
        log.info("启用禁用员工账号：{},{}",status,id);
        employeeService.startOrStop(status,id);
        return R.success();
    }


    /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 员工的信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "getById->根据id查询员工信息")
    @ApiOperationSupport(order=6)
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }

    /**
     * 修改员工信息
     * @param employeeDTO DTO
     * @return 修改是否成功
     */
    @PutMapping
    @Operation(summary = "update->修改员工信息")
    @ApiOperationSupport(order=7)
    public R update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return R.success();
    }
}
