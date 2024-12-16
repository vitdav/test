package com.sgugo.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sgugo.sky.constant.MessageConstant;
import com.sgugo.sky.constant.StatusConstant;
import com.sgugo.sky.dto.DishDTO;
import com.sgugo.sky.dto.DishPageQueryDTO;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.entity.DishFlavor;
import com.sgugo.sky.entity.Setmeal;
import com.sgugo.sky.exception.DeletionNotAllowedException;
import com.sgugo.sky.mapper.DishFlavorMapper;
import com.sgugo.sky.mapper.DishMapper;
import com.sgugo.sky.mapper.SetmealDishMapper;
import com.sgugo.sky.mapper.SetmealMapper;
import com.sgugo.sky.result.Rs;
import com.sgugo.sky.service.DishService;
import com.sgugo.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增菜品（包含对应的口味）
     * @param dishDTO DTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //1. 将DTO转为PO（entity），因为Dao层操作数据库要操作PO
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //2. 向菜品表插入菜品数据，并获取插入后自增的主键
        dishMapper.insert(dish);

        //获取插入的id，提供给口味表插入使用
        Long dishId = dish.getId();

        //3. 从dishDTO中获取口味信息，并插入到口味表
        List<DishFlavor> flavors = dishDTO.getFlavors();

        //口味数据是允许为空的，因此要先判断一下
        if(flavors != null && flavors.size()>0){
            //遍历口味集合，为每条数据添加Dish表的关联ID
            flavors.forEach(dishFlavor -> {
                    dishFlavor.setDishId(dishId);
            });
            //进行批量插入
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 菜品分页查询
     * @param dto dto
     * @return 分页查询的结果
     */
    @Override
    public Rs pageQuery(DishPageQueryDTO dto) {
        //使用PageHelper获取分页条件
        PageHelper.startPage(dto.getPage(),dto.getPageSize());

        //调用 Mapper 获取分页数据
        Page<DishVO> page = dishMapper.pageQuery(dto);

        return new Rs(page.getTotal(), page.getResult());
    }

    /**
     * 根据id删除菜品
     * @param ids id组成的集合
     */
    @Override
    @Transactional //批量删除要开启事务
    public void deleteBatch(List<Long> ids) {
        //1. 第一次循环判断菜品是否能删除：是否为起售菜品
        for(Long id : ids){
            //根据id,查询菜品的所有信息
            Dish dish = dishMapper.getById(id);
            //判断菜品信息的起售状态，若在起售中，直接抛出自定义异常
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //2. 第二次判断菜品是否能删除，是否被套餐关联
        //查询关联表，setmeal_dish，根据菜品id集合，查询对应的套餐id集合
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds != null && setmealIds.size()>0){
            //因为是批量删除，因此有一个菜品被套餐关联，所有菜品都不能被删除，无需遍历判断
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

        }
        //3. 调用Mapper，循环删除菜品和菜品关联的口味
        for(Long id : ids){
            // 根据id 删除菜品
            dishMapper.deleteById(id);
            // 根据id 删除菜品关联的口味
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * 根据Id获取菜品信息（包含口味）
     * @param id 菜品id
     * @return 菜品VO
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        //1. 根据id查询菜品数据
        Dish dish = dishMapper.getById(id);

        //2. 根据id查询菜品的口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        //3. 将数据封装到 dishVO
        DishVO vo = new DishVO();
        BeanUtils.copyProperties(dish,vo);
        vo.setFlavors(dishFlavors);

        return vo;
    }

    /**
     * 更新菜品信息（包含口味）
     * @param dishDTO 菜品DTO
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        //1. 获取菜品实体类
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //2. 修改菜品表的信息
        dishMapper.update(dish);

        //3. 删除菜品原有的口味信息
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //4. 重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size()>0){
            flavors.forEach(dishFlavor->{
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 查询菜品信息（包含菜品的口味）
     * @param dish 查询条件
     * @return 菜品信息列表
     */
    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        //查询数据库，获取菜品列表（不包含口味）
        List<Dish> dishList = dishMapper.list(dish);

        //创建一个包含口味信息的菜品集合，以待添加信息
        ArrayList<DishVO> dishVOList = new ArrayList<>();

        //遍历查询到的菜品列表，为每个菜品获取口味信息
        for (Dish d : dishList){
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            //为菜品VO添加口味属性
            dishVO.setFlavors(flavors);

            //将拥有口味信息的菜品添加到集合总
            dishVOList.add(dishVO);
        }

        //将包含口味信息的菜品集合返回
        return dishVOList;
    }

    /**
     * 菜品起售停售
     * @param status 待修改的状态
     * @param id 菜品的id
     */
    @Transactional
    public void startOrStop(Integer status, Long id){
        Dish dish = Dish.builder().id(id).status(status).build();

        //对菜品进行停售
        dishMapper.update(dish);

        //如果是停售操作，还需要将包含当前菜品的套餐也停售
        if(status == StatusConstant.DISABLE){
            List<Long> dishIds = new ArrayList<>();
            dishIds.add(id);
            //select setmeal_id from setmeal_dish where dish_id in(?,?,?)
            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);

            //判断是否有套餐，有的话就就停售
            if(setmealIds != null && setmealIds.size()>0){
                for(Long setmealId : setmealIds){
                    Setmeal setmeal = Setmeal.builder().id(setmealId).status(StatusConstant.DISABLE).build();
                    setmealMapper.update(setmeal);
                }
            }
        }
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类id
     * @return 菜品列表
     */
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder().categoryId(categoryId).status(StatusConstant.ENABLE).build();
        return dishMapper.list(dish);
    }
}
