package com.sgugo.sky.controller.user;

import com.sgugo.sky.context.BaseContext;
import com.sgugo.sky.entity.AddressBook;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Schema(name="AddressBook-用户地址薄")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     * @param addressBook 地址表实体类
     * @return 添加是否成功
     */
    @PostMapping
    @Operation(summary = "save->新增地址")
    public R save(@RequestBody AddressBook addressBook){
        addressBookService.save(addressBook);
        return R.success();
    }

    /**
     * 查看当前登录用户的所有地址信息
     * @return 地址列表
     */
    @GetMapping("/list")
    @Operation(summary = "list->查看用户所有地址")
    public R<List<AddressBook>> list(){
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return R.success(list);
    }

    /**
     * 根据主键id查询地址信息
     * @param id 主键
     * @return 地址信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "getById->根据主键id查询地址信息")
    public R<AddressBook> getById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    /**
     * 根据主键id更新地址信息
     * @param addressBook 更新后的地址信息
     * @return 更新是否成功
     */
    @PutMapping
    @Operation(summary = "update->根据主键id修改用户信息")
    public R update(@RequestBody AddressBook addressBook){
        addressBookService.update(addressBook);
        return R.success();
    }

    /**
     * 设置默认地址
     * @param addressBook 地址实体类，待设置为默认地址
     * @return 是否设置成功
     */
    @PutMapping("/default")
    @Operation(summary = "setDefault->设置默认地址")
    public R setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);
        return R.success();
    }

    /**
     * 查询默认地址
     * @return 地址信息
     */
    @GetMapping("/default")
    @Operation(summary = "getDefault->查询默认地址")
    public R<AddressBook> getDefault(){
        //构建查询参数：用户id和is_default=1
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getId());
        //查询默认地址（虽然返回的是List，但只有一个默认地址）
        List<AddressBook> list = addressBookService.list(addressBook);

        //判断是否有默认地址
        if(list != null && list.size() == 1){
            return R.success(list.get(0));
        }

        return R.error("没有查询到默认地址");
    }

    /**
     * 根据主键 id 删除地址
     * @param id 主键id
     * @return 是否删除成功
     */
    @DeleteMapping
    @Operation(summary = "deleteById->删除地址")
    public R deleteById(Long id){
        addressBookService.deleteById(id);

        return R.success();
    }
}
