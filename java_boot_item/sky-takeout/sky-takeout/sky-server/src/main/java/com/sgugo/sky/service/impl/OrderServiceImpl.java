package com.sgugo.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sgugo.sky.constant.MessageConstant;
import com.sgugo.sky.constant.OrderConstant;
import com.sgugo.sky.context.BaseContext;
import com.sgugo.sky.dto.OrdersPaymentDTO;
import com.sgugo.sky.dto.OrdersSubmitDTO;
import com.sgugo.sky.entity.*;
import com.sgugo.sky.exception.AddressBookBusinessException;
import com.sgugo.sky.exception.OrderBusinessException;
import com.sgugo.sky.exception.ShoppingCartBusinessException;
import com.sgugo.sky.mapper.*;
import com.sgugo.sky.service.OrderService;
import com.sgugo.sky.utils.WeChatPayUtil;
import com.sgugo.sky.vo.OrderPaymentVO;
import com.sgugo.sky.vo.OrderSubmitVO;
import com.sgugo.sky.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private WebSocketServer webSocketServer;
    /**
     * 用户下单
     * @param ordersSubmitDTO DTO
     * @return VO
     */
    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        //1. 对订单的参数进行异常处理：地址为空、购物车没商品等
        //1.1 根据订单提供的地址id参数，获取用户的地址信息
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        // 判断地址是否存在
        if(addressBook == null){
            //地址为空，则抛出业务异常
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //1.2 检测用户的收货地址是否超过配送范围，这里暂不处理

        //1.3 查询当前用户的购物车数据
        //从上下文线程中获取用户的id,通过该id查询用户的购物车数据
        Long userId = BaseContext.getId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        //判断购物车是否有数据
        if(shoppingCartList == null || shoppingCartList.size() == 0){
            //购物车无数据，抛出异常
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2. 排除异常后，将参数里的订单数据插入到订单表
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(OrderConstant.UN_PAID);
        orders.setStatus(OrderConstant.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);
        orderMapper.insert(orders);

        //3. 将商品信息储存到订单明细表中（可能有多条商品信息）
        ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        //遍历购物车，将购物车里的数据，将这些数据储存到订单明细表
        for(ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            //将关联的订单表的id和购物车的菜品加入菜单明细表的实体类中
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        //插入到数据库
        orderDetailMapper.insertBatch(orderDetailList);

        //4. 清空当前用户的购物车数据
        shoppingCartMapper.deleteByUserId(userId);

        //5. 封装要返回的VO，进行返回
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId()) //订单id
                .orderTime(orders.getOrderTime())//下单时间
                .orderNumber(orders.getNumber())//订单号
                .orderAmount(orders.getAmount())//订单总额
                .build();

        return orderSubmitVO;
    }

    /**
     * 订单支付
     * @param ordersPaymentDTO 支付信息
     * @return 小程序调用 wx.requestPayment 使用的支付参数
     * @throws Exception 交易失败
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        //1. 根据线程上下文获取当前登录用户id，并根据id获取用户数据（要用到openid）
        Long userId = BaseContext.getId();
        User user = userMapper.getById(userId);

        //2. 调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //订单号
                new BigDecimal(ordersPaymentDTO.getTotal()), //支付金额，单位 元
                "苍穹外卖订单", //商品描述
                user.getOpenid() //微信用户的openid
        );
        //说明：JSONObject是工具类中封装好的数据，用于返回给微信小程序，
        //小程序获取到参数后可以通过`requestPayment`方法访问微信服务器，调起微信支付

        //3. 判断jsonObject数据，是否已支付过，避免重复支付
        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            //抛出业务异常类，提醒该订单已支付
            throw new OrderBusinessException("该订单已支付");
        }

        //4. 将JSONObject转为VO，返回给接口
        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 修改订单状态，向后台发送提醒
     * @param outTradeNo 订单号
     */
    @Override
    public void paySuccess(String outTradeNo) {
        Long userId = BaseContext.getId();
        //根据订单号和用户id查询该订单
        //PS：如果订单号是唯一的，仅根据订单号既可查询
        Orders ordersDB = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        //根据订单id，更新订单的状态：字符方式、支付状态、支付时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(OrderConstant.TO_BE_CONFIRMED)
                .payStatus(OrderConstant.PAID)
                .checkoutTime(LocalDateTime.now()).build();
        //更新订单状态
        orderMapper.update(orders);

        //通过websocket向客户端浏览器推送消息：来电提醒，有新订单
        Map map = new HashMap();
        //1表示来单提醒 2表示客户催单
        map.put("type",1);
        map.put("orderId",ordersDB.getId());
        map.put("content","订单号：" + outTradeNo);

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }

    /**
     * 客户催单
     * @param id 订单id
     */
    @Override
    public void reminder(Long id) {
        //根据订单id,查询订单信息
        Orders ordersDB = orderMapper.getById(id);

        //校验订单是否存在
        if(ordersDB == null){
            //不存在就抛出业务异常
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        //使用websocket向后台进行催单
        Map map = new HashMap();
        map.put("type",2); //1表示来单提醒 2表示客户催单
        map.put("orderId",id);
        map.put("content","订单号：" + ordersDB.getNumber());

        //通过websocket向客户端浏览器推送消息
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }
}
