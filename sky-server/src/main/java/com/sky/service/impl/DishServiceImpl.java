package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Transactional
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.add(dish);
        Long dishId = dish.getId();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.addPatch(dishFlavors);
        }

    }

    public PageResult pageSearch(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageSearch(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public List<Dish> list(Long categoryId) {
        return dishMapper.findByCategoryId(categoryId);
    }

    public DishVO get(Long id) {
        Dish dish = dishMapper.findById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.findByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Transactional
    public void update(DishVO dishVO) {
        Dish dish = dishMapper.findById(dishVO.getId());
        BeanUtils.copyProperties(dishVO, dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteByDishId(dishVO.getId());
        List<DishFlavor> dishFlavors = dishVO.getFlavors();
        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishVO.getId());
            });
        }
        dishFlavorMapper.addPatch(dishFlavors);
    }

    public void updateStatus(Integer status, Long id) {
        Dish dish = dishMapper.findById(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.findById(id);
            if (setmealDishMapper.countByDishId(id) != 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            dishFlavorMapper.deleteByDishId(id);
            dishMapper.deleteById(id);
        }
    }
}
