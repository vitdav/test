package com.sgugo.sky.task;

import com.sgugo.sky.constant.OrderConstant;
import com.sgugo.sky.entity.Orders;
import com.sgugo.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，定时处理订单状态
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单的方法
     */
    @Scheduled(cron = "0 * * * * ? ") //每分钟触发一次
    public void processTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        //获取15分钟前的时间
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        //查询15分钟前下单，且未支付的所有订单
        // select * from orders where status = ? and order_time < (当前时间 - 15分钟)
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(OrderConstant.PENDING_PAYMENT, time);

        //判断是否有未支付的订单
        if(ordersList != null && ordersList.size() > 0){
            //遍历查询到的订单，将他们的状态设置为已取消
            for (Orders orders : ordersList) {
                orders.setStatus(OrderConstant.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                //更新信息
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理一直处于派送中状态的订单
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点触发一次
    public void processDeliveryOrder(){
        log.info("定时处理处于派送中的订单：{}",LocalDateTime.now());
        //当前是凌晨一点，清理昨天的订单，所以要获取一小时前的时间
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        //查询一小时前的订单，且状态为派送中
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(OrderConstant.DELIVERY_IN_PROGRESS, time);
        //判断是否查询到了订单
        if(ordersList != null && ordersList.size() > 0){
            //遍历订单，将他们的状态修噶以为已完成
            for (Orders orders : ordersList) {
                orders.setStatus(OrderConstant.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }
}
