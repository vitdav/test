package com.sgugo.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="OrderPaymentVO：小程序支付参数")
public class OrderPaymentVO implements Serializable {

    @Schema(description = "随机字符串",defaultValue = "1232334abcd")
    private String nonceStr;

    @Schema(description = "签名")
    private String paySign;

    @Schema(description = "时间戳")
    private String timeStamp;

    @Schema(description = "签名算法")
    private String signType;

    @Schema(description = "微信服务器返回的 prepay_id")
    private String packageStr;
}
