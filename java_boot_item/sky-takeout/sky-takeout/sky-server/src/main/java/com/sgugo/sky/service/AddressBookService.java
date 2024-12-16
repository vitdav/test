package com.sgugo.sky.service;

import com.sgugo.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     * @param addressBook 地址表实体类
     */
    void save(AddressBook addressBook);

    /**
     * 查看当前登录用户的所有地址信息
     * @param addressBook 查询条件，包含用户id
     * @return 地址列表
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 根据主键id查询地址信息
     * @param id 主键
     * @return 地址信息
     */
    AddressBook getById(Long id);

    /**
     * 根据主键id更新地址信息
     * @param addressBook 更新后的地址信息
     */
    void update(AddressBook addressBook);

    /**
     * 设置默认地址
     * @param addressBook 地址实体类，待设置为默认地址
     */
    void setDefault(AddressBook addressBook);

    /**
     * 根据主键 id 删除地址
     * @param id 主键id
     */
    void deleteById(Long id);
}
