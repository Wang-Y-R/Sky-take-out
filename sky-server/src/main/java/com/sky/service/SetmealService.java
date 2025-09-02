package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void addSetmeal(SetmealDTO setmealDTO);

    PageResult pageSearch(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO get(Long id);

    void update(SetmealDTO setmealDTO);

    void updateStatus(Integer status, Long id);

    void delete(List<Long> ids);
}
