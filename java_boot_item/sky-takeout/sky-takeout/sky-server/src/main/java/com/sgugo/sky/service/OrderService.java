package com.sgugo.sky.service;

import com.sgugo.sky.dto.OrdersPaymentDTO;
import com.sgugo.sky.dto.OrdersSubmitDTO;
import com.sgugo.sky.vo.OrderPaymentVO;
import com.sgugo.sky.vo.OrderSubmitVO;

public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO DTO
     * @return VO
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO 支付信息
     * @return 小程序调用 wx.requestPayment 使用的支付参数
     * @throws Exception 交易失败
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo 订单号
     */
    void paySuccess(String outTradeNo);

    /**
     * 客户催单
     * @param id 订单id
     */
    void reminder(Long id);
}
