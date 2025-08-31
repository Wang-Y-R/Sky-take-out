package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result<PageResult> pageSearch(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询");
        PageResult pageResult = categoryService.pageSearch(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping()
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("分类修改");
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status,Long id) {
        log.info("分类修改状态");
        categoryService.updateStatus(status,id);
        return Result.success();
    }

    @PostMapping()
    public Result<String> add(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类");
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    public Result<String> delete(Long id) {
        log.info("删除分类");
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("分类查询");
        return Result.success(categoryService.list(type));
    }
}
