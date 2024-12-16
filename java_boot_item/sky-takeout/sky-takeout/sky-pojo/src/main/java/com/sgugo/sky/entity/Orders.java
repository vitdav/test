package com.sgugo.sky.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="Orders：订单表实体类")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id",defaultValue = "1")
    private Long id;

    @Schema(description = "订单号",defaultValue = "1010101010")
    private String number;

    @Schema(description = "订单状态：1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款",defaultValue = "2")
    private Integer status;

    @Schema(description = "下单用户id",defaultValue = "1")
    private Long userId;

    @Schema(description = "地址id",defaultValue = "1")
    private Long addressBookId;

    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    @Schema(description = "结账时间")
    private LocalDateTime checkoutTime;

    @Schema(description = "支付方式：1微信，2支付宝",defaultValue = "1")
    private Integer payMethod;

    @Schema(description = "支付状态：0未支付 1已支付 2退款",defaultValue = "0")
    private Integer payStatus;

    @Schema(description = "实收金额",defaultValue = "20")
    private BigDecimal amount;

    @Schema(description = "备注",defaultValue = "多放辣")
    private String remark;

    @Schema(description = "用户名",defaultValue = "吴彦祖")
    private String userName;

    @Schema(description = "手机号",defaultValue = "13000000000")
    private String phone;

    @Schema(description = "用户地址",defaultValue = "深圳")
    private String address;

    @Schema(description = "收货人",defaultValue = "吴彦祖")
    private String consignee;

    @Schema(description = "订单取消原因",defaultValue = "不想要了")
    private String cancelReason;

    @Schema(description = "订单拒绝原因",defaultValue = "超出配送范围")
    private String rejectionReason;

    @Schema(description = "订单取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    @Schema(description = "配送状态：1立即送出，0选择具体时间",defaultValue = "1")
    private Integer deliveryStatus;

    @Schema(description = "送达时间")
    private LocalDateTime deliveryTime;

    @Schema(description = "打包费",defaultValue = "3")
    private int packAmount;

    @Schema(description = "餐具数量",defaultValue = "1")
    private int tablewareNumber;

    @Schema(description = "餐具数量状态：1按餐量提供，0选择具体数量",defaultValue = "1")
    private Integer tablewareStatus;
}
