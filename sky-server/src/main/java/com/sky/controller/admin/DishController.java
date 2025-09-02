package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.annotation.Log;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @PostMapping()
    @Log("添加菜品")
    public Result<String> add(@RequestBody DishDTO dishDTO) {
        dishService.add(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Log("菜品按页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.pageSearch(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    @Log("菜品分类查询")
    public Result<List<Dish>> list(Long categoryId) {
        return Result.success(dishService.list(categoryId));
    }

    @GetMapping("/{id}")
    @Log("菜品id查询")
    public Result<DishVO> get(@PathVariable Long id) {
        return Result.success(dishService.get(id));
    }

    @PutMapping()
    @Log("更新菜品")
    public Result<String> update(@RequestBody DishVO dishVO) {
        dishService.update(dishVO);
        return Result.success();
    }

    @PostMapping("status/{status}")
    @Log("设置菜品状态")
    public Result<String> updateStatus(@PathVariable Integer status,Long id) {
        dishService.updateStatus(status,id);
        return Result.success();
    }

    @DeleteMapping()
    @Log("批量删除")
    public Result<String> delete(@RequestParam List<Long> ids) {
        dishService.delete(ids);
        return Result.success();
    }
}
