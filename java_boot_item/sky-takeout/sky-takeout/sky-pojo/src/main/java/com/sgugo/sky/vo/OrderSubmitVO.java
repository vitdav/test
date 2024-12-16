package com.sgugo.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="OrderSubmitVO：下单成功页面展示的待支付信息")
public class OrderSubmitVO {

    @Schema(description = "订单id",defaultValue = "1")
    private Long id;

    @Schema(description = "订单号",defaultValue = "1010101010")
    private String orderNumber;

    @Schema(description = "订单金额",defaultValue = "30")
    private BigDecimal orderAmount;

    @Schema(description = "下单时间")
    private LocalDateTime orderTime;
}
