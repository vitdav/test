package com.sgugo.sky.service.impl;

import com.sgugo.sky.context.BaseContext;
import com.sgugo.sky.entity.AddressBook;
import com.sgugo.sky.mapper.AddressBookMapper;
import com.sgugo.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     * @param addressBook 地址表实体类
     */
    @Override
    public void save(AddressBook addressBook) {
        //从线程上线文获取当前用户的id,添加到实体类
        addressBook.setUserId(BaseContext.getId());
        //默认情况下，新增的地址非默认地址
        addressBook.setIsDefault(0);
        //将地址信息插入到数据库
        addressBookMapper.insert(addressBook);

    }

    /**
     * 查看当前登录用户的所有地址信息
     * @param addressBook 查询条件，包含用户id
     * @return 地址列表
     */
    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    /**
     * 根据主键id查询地址信息
     * @param id 主键
     * @return 地址信息
     */
    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        return addressBook;
    }

    /**
     * 根据主键id更新地址信息
     * @param addressBook 更新后的地址信息
     */
    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook 地址实体类，待设置为默认地址
     */
    @Override
    public void setDefault(AddressBook addressBook) {
        //1.先将该用户的所有地址改为非默认地址
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);
        //2. 将当前（参数中的）地址改为默认地址
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据主键 id 删除地址
     * @param id 主键id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }
}
