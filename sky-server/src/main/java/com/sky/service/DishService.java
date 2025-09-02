package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void add(DishDTO dishDTO);

    PageResult pageSearch(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> list(Long categoryId);

    DishVO get(Long id);

    void update(DishVO dishVO);

    void updateStatus(Integer status, Long id);

    void delete(List<Long> ids);
}
