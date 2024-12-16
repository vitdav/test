package com.sgugo.sky.mapper;

import com.sgugo.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 新增地址
     * @param addressBook 地址表实体类
     */
    @Insert("insert into address_book" +
            " (user_id, consignee, phone, sex, province_code, province_name, city_code," +
            " city_name, district_code,district_name, detail, label, is_default)" +
            " values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}," +
            " #{provinceName}, #{cityCode}, #{cityName}," +
            " #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(AddressBook addressBook);

    /**
     * 根据动态条件查看当前登录用户的所有地址信息
     * @param addressBook 查询条件，
     * @return 地址列表
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 根据主键id查询地址信息
     * @param id 主键
     * @return 地址信息
     */
    @Select("select * from address_book where id = #{id}")
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
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);

    /**
     * 根据主键 id 删除地址
     * @param id 主键id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
}
