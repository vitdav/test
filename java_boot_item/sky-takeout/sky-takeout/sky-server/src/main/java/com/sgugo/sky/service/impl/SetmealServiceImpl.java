package com.sgugo.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sgugo.sky.constant.MessageConstant;
import com.sgugo.sky.constant.StatusConstant;
import com.sgugo.sky.dto.SetmealDTO;
import com.sgugo.sky.dto.SetmealPageQueryDTO;
import com.sgugo.sky.entity.Dish;
import com.sgugo.sky.entity.Setmeal;
import com.sgugo.sky.entity.SetmealDish;
import com.sgugo.sky.exception.DeletionNotAllowedException;
import com.sgugo.sky.exception.SetmealEnableFailedException;
import com.sgugo.sky.mapper.DishMapper;
import com.sgugo.sky.mapper.SetmealDishMapper;
import com.sgugo.sky.mapper.SetmealMapper;
import com.sgugo.sky.service.SetmealService;
import com.sgugo.sky.vo.DishItemVO;
import com.sgugo.sky.vo.PageResultVO;
import com.sgugo.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO DTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //向套餐表插入套餐信息
        setmealMapper.insert(setmeal);

        //获取生成的套餐id
        Long setmealId = setmeal.getId();

        //获取DTO里，套餐包含的所有菜品信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        //遍历所有菜品信息，为菜品信息添加所属的套餐id
        setmealDishes.forEach(setmealDish->{
            setmealDish.setSetmealId(setmealId);
        });

        //将包含套餐id的菜品信息，添加到套餐和菜品关系表中
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     *  根据条件(分类id)查询套餐
     * @param setmeal 查询条件
     * @return 套餐列表信息
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    /**
     * 根据套餐id，查询套餐内的菜品信息
     * @param id 套餐id
     * @return 套餐内包含的菜品信息
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO 查询条件
     * @return 查询结果
     */
    @Override
    public PageResultVO pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //获取查询的页数和每页显示的条数
        int pageNum = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();

        //进行分页查询
        PageHelper.startPage(pageNum,pageSize);
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        //返回分页数据，包括分页总数
        return new PageResultVO(page.getTotal(),page.getResult());
    }

    /**
     * 删除套餐（可批量删除）
     * @param ids 待删除的id列表
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //遍历查询所有的套餐id，起售的套餐不能删除
        ids.forEach(id->{
            //根据id查询套餐信息，判断套餐的状态
            Setmeal setmeal = setmealMapper.getById(id);
            if(StatusConstant.ENABLE == setmeal.getStatus()){
                //套餐在启售中，就抛出业务异常，不能删除(批量删除整体会失败）
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        //遍历id，删除所有的id
        ids.forEach(id->{
            //删除套餐表中的数据
            setmealMapper.deleteById(id);
            //删除套餐菜品关系表中的数据
            setmealDishMapper.deleteBySetmealId(id);
        });
    }

    /**
     * 根据套餐id，查询套餐信息和套餐关联的菜品信息
     * @param id 套餐id
     * @return 套餐信息
     */
    @Override
    public SetmealVO getByIdWithDish(Long id) {
        SetmealVO setmealVO = setmealMapper.getByIdWithDish(id);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO 修改后的套餐信息
     */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //1. 先修改套餐表，执行update
        setmealMapper.update(setmeal);

        //2. 根据套餐id,删除套餐和菜品的关联关系
        //获取套餐的id
        Long setmealId = setmealDTO.getId();
        //删除套餐信息（同时也会删除套菜品关联信息）
        setmealDishMapper.deleteBySetmealId(setmealId);

        //3. 根据修改后的信息，从新插入套餐和菜品的关联关系
        //获取修改后的套餐里的菜品信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //遍历菜品信息，为菜品信息添加关联的套餐id
        setmealDishes.forEach(setmealDish->{
            setmealDish.setSetmealId(setmealId);
        });
        //将新的套餐和菜品关联信息插入到数据库
        setmealDishMapper.insertBatch(setmealDishes);
    }


    /**
     * 修改套餐状态
     * @param status 修改后的状态，1启售 0停售
     * @param id 套餐id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        //起售套餐时，判断套餐内是否有停售菜品
        if(status == StatusConstant.ENABLE){
            //根据套餐id,获取套餐内的菜品信息
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList != null && dishList.size()>0){
                //遍历菜品信息，判断菜品是否停售了，停售就抛出异常
                dishList.forEach(dish->{
                   if(StatusConstant.DISABLE == dish.getStatus()){
                       throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                   }
                });
            }
        }

        //修改套餐状态信息
        Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
        setmealMapper.update(setmeal);
    }


}
