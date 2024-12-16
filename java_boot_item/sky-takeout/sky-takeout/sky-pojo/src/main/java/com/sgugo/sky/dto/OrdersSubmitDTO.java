package com.sgugo.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name="OrdersSubmitDTO：新增订单的DTO")
public class OrdersSubmitDTO {

    @Schema(description = "地址id",defaultValue = "1")
    private Long addressBookId;

    @Schema(description = "付款方式",defaultValue = "1")
    private int payMethod;

    @Schema(description = "备注",defaultValue = "1")
    private String remark;

    @Schema(description = "预计送达时间",defaultValue = "1")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;

    @Schema(description = "配送状态  1立即送出  0选择具体时间",defaultValue = "1")
    private Integer deliveryStatus;

    @Schema(description = "餐具数量",defaultValue = "1")
    private Integer tablewareNumber;

    @Schema(description = "餐具数量状态  1按餐量提供  0选择具体数量",defaultValue = "1")
    private Integer tablewareStatus;

    @Schema(description = "打包费",defaultValue = "1")
    private Integer packAmount;

    @Schema(description = "总金额",defaultValue = "1")
    private BigDecimal amount;
}
