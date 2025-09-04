package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.add(setmeal);
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        setmealDishList.forEach(setmealDish ->
        {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.addPatch(setmealDTO.getSetmealDishes());
    }

    public PageResult pageSearch(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.pageSearch(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public SetmealVO get(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setmealMapper.get(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = setmealMapper.get(setmealDTO.getId());
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealDTO.getId());
            });
        }
        setmealDishMapper.addPatch(setmealDishes);
    }

    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal = setmealMapper.get(id);
        if (status == StatusConstant.ENABLE) {
            //检查是否有菜品未起售
            List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
            for (SetmealDish setmealDish : setmealDishes) {
                Dish dish = dishMapper.findById(setmealDish.getDishId());
                if (dish.getStatus() == StatusConstant.DISABLE) {
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id: ids) {
            Setmeal setmeal = setmealMapper.get(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
            setmealDishMapper.deleteBySetmealId(id);
            setmealMapper.deleteById(id);
        }

    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
