package com.sgugo.sky.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name="OrdersPaymentDTO：支付接口的DTO")
public class OrdersPaymentDTO implements Serializable {
    @Schema(description = "订单号", defaultValue = "10101010")
    private String orderNumber;

    @Schema(description = "支付方式，可选，目前只支持微信", defaultValue = "1")
    private Integer payMethod;

    //案例里目前未至此该参数，但应该有
    @Schema(description = "支付金额",defaultValue = "100")
    private Long total;
}
