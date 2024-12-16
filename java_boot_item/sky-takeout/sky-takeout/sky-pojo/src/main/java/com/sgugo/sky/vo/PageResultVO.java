package com.sgugo.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 统一封装分页查询结果的VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="PageResultVO：统一封装分页查询结果的VO")
public class PageResultVO implements Serializable {

    //总记录数
    @Schema(description = "总记录数", defaultValue = "100", required=true)
    private long total;

    //当前页数据集合
    @Schema(description = "当前页数据集合", required=true)
    private List records;
}
