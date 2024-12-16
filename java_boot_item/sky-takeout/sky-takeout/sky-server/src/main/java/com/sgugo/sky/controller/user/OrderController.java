package com.sgugo.sky.controller.user;

import com.sgugo.sky.dto.OrdersPaymentDTO;
import com.sgugo.sky.dto.OrdersSubmitDTO;
import com.sgugo.sky.result.R;
import com.sgugo.sky.service.OrderService;
import com.sgugo.sky.vo.OrderPaymentVO;
import com.sgugo.sky.vo.OrderSubmitVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Tag(name="Order-订单模块")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param ordersSubmitDTO DTO
     * @return VO
     */
    @PostMapping("/submit")
    @Operation(summary = "submit->用户下单")
    public R<OrderSubmitVO> submit (@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("用户下单，参数为：{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return R.success(orderSubmitVO);
    }

    /**
     * 订单支付
     * @param ordersPaymentDTO 支付信息
     * @return 小程序调用 wx.requestPayment 使用的支付参数
     * @throws Exception 交易失败
     */
    @PostMapping("/payment")
    @Operation(summary = "payment->订单支付")
    public R<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception{
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        return R.success(orderPaymentVO);
    }

    /**
     * 客户催单
     * @param id 订单的id
     * @return 是否催单成功
     */
    @GetMapping("/reminder/{id}")
    @Operation(summary = "reminder->客户催单")
    public R reminder(@PathVariable("id") Long id){
        orderService.reminder(id);
        return R.success();
    }
}
