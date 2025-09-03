package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @PostMapping()
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> pageSearch(SetmealPageQueryDTO setmealPageQueryDTO) {
        return Result.success(setmealService.pageSearch(setmealPageQueryDTO));
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> get(@PathVariable Long id) {
        return Result.success(setmealService.get(id));
    }

    @PutMapping()
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result<SetmealVO> updateStatus(@PathVariable Integer status,Long id) {
        setmealService.updateStatus(status,id);
        return Result.success();
    }

    @DeleteMapping()
    public Result<String> delete(@RequestParam List<Long> ids) {
        setmealService.delete(ids);
        return Result.success();
    }
}
