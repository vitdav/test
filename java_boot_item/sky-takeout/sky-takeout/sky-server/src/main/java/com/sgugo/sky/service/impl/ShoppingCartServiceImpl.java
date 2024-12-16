package com.sgugo.sky.service.impl;

import com.sgugo.sky.context.BaseContext;
import com.sgugo.sky.dto.ShoppingCartDTO;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.entity.Setmeal;
import com.sgugo.sky.entity.ShoppingCart;
import com.sgugo.sky.mapper.DishMapper;
import com.sgugo.sky.mapper.SetmealMapper;
import com.sgugo.sky.mapper.ShoppingCartMapper;
import com.sgugo.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO 待添加的内容
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //1. 获取当前用户的id
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //获取购物车用户的id,通过线程上下文
        Long userId = BaseContext.getId();
        //为实体类添加id
        shoppingCart.setUserId(userId);

        //2. 判断当前加入到购物车中的商品是否已经存在了
        //从数据库查询当前用户当前的当前菜品（或套餐）
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //3. 遍历查询到的列表，判断菜品（套餐）是否存在
        if(list != null && list.size() >0){
            //3.1 商品存在，就为该商品的数量+1
            //因为设计的SQL查询结果是list,所以要get(0)
            ShoppingCart cart = list.get(0);
            //将该商品的数量+1，该条数据库记录的商品数量字段+1
            cart.setNumber(cart.getNumber()+1);
            //更新数据：update shopping_cart set number = ? where id = ?
            shoppingCartMapper.updateNumberById(cart);
        }else{
            //3.2 商品不存在，就将该商品加入到数据库
            //判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null){
                //3.2.1 有dishId,说明是菜品
                //从 Dish 数据表获取该菜品的信息（价格、图片、名字）
                Dish dish = dishMapper.getById(dishId);
                //将查询到的菜品信息添加到ShoppingCart实体类
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //3.2.2 没有dishId,说明是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                //从 Setmeal 数据表获取该套餐的信息（价格、图片、名字）
                Setmeal setmeal = setmealMapper.getById(setmealId);
                //将查询到的套餐信息添加到ShoppingCart实体类
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }

            //3.2.3 将新增的商品添加到数据库
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }

    }

    /**
     * 查看购物车
     * @return 购物车信息
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        //根据线程上下文对象获取当前用户的id
        Long userId = BaseContext.getId();

        //根据用户的id,从购物车数据表中查询该用户相关的所有商品进行展示
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(userId).build();

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    /**
     * 删除购物车中的一个商品
     * @param shoppingCartDTO 被删除的商品信息
     */
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //设置查询的条件，储存在线程中的用户id
        shoppingCart.setUserId(BaseContext.getId());

        //以用户id和商品id为条件，查询购物车表中用户购物车里的该商品
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //判断购物车中该商品的数量，以决定是将商品移除还是数量-1
        if(list != null && list.size() > 0){
            shoppingCart = list.get(0);
            //判断目标商品的数量是否是1个
            Integer number = shoppingCart.getNumber();
            if(number == 1){
                //只有一个该商品，就移除该商品,根据该条记录的主键删除
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }else{
                //不止一个该商品，就将该商品的数量-1，修改该条记录的份数字段
                shoppingCart.setNumber(shoppingCart.getNumber()-1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }
        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void cleanShoppingCart() {
        //获取线程上下文保存的用户id,删除该用户在购物车数据表中的所有的商品信息
        Long userId = BaseContext.getId();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
