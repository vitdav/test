package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="AddressBook-地址簿实体类")
public class AddressBook implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id->自增主键",defaultValue = "1")
    private Long id;

    @Schema(description = "userId->用户id",defaultValue = "1")
    private Long userId;

    @Schema(description = "consignee->收货人姓名",defaultValue = "毛利")
    private String consignee;

    @Schema(description = "phone->手机号",defaultValue = "15212345678",required = true)
    private String phone;

    @Schema(description = "sex->性别 1男 0女",defaultValue = "1")
    private String sex;

    @Schema(description = "provinceCode->省级编号",defaultValue = "10")
    private String provinceCode;

    @Schema(description = "provinceName->省名称",defaultValue = "广东")
    private String provinceName;


    @Schema(description = "cityCode->市级编号",defaultValue = "20")
    private String cityCode;

    @Schema(description = "cityName->市名称",defaultValue = "深圳")
    private String cityName;

    @Schema(description = "cityName->区级编号",defaultValue = "30")
    private String districtCode;

    @Schema(description = "cityName->区名称",defaultValue = "南山区")
    private String districtName;

    @Schema(description = "cityName->详细地址",defaultValue = "南山科技园3栋301")
    private String detail;

    //标签
    @Schema(description = "地址标签：公司、家、学校",defaultValue = "公司")
    private String label;

    @Schema(description = "isDefault->是否是默认地址 0否 1是",defaultValue = "1",required = true)
    private Integer isDefault;
}
