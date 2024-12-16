package com.sgugo.sky.mapper;

import com.sgugo.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单状态和下单时间查询订单
     * @param status 订单状态（为支付）
     * @param orderTime 下单时间（15分钟前）
     * @return 订单列表
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 修改订单信息
     * @param orders 订单实体类
     */
    void update(Orders orders);

    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber 订单号
     * @param userId 用户id
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 根据订单id查询订单
     * @param id 订单id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);
}
