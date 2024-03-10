package com.sgugo.sky.service;

import com.sgugo.sky.dto.DishDTO;
import com.sgugo.sky.dto.DishPageQueryDTO;
import com.sgugo.sky.result.Rs;
import com.sgugo.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.sql.PseudoColumnUsage;
import java.util.List;

@Service
public interface DishService {
    /**
     * 新增菜品和对应的口味
     * @param dishDTO DTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dto dto
     * @return 分页查询的结果
     */
    Rs pageQuery(DishPageQueryDTO dto);

    /**
     * 菜品删除（批量）
     * @param ids 菜品id集合
     */
    void deleteBatch(List<Long> ids);


    /**
     * 根据Id获取菜品信息（包含口味）
     * @param id 菜品id
     * @return 菜品VO
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息（包含口味）
     * @param dishDTO 菜品DTO
     */
    void updateWithFlavor(DishDTO dishDTO);
}
