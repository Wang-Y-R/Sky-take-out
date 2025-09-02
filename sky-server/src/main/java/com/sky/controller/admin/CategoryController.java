package com.sky.controller.admin;

import com.sky.annotation.Log;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.vo.DishVO;
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
    @Log("分类分页查询")
    public Result<PageResult> pageSearch(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageSearch(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping()
    @Log("分类修改")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @Log("分类修改状态")
    public Result<String> updateStatus(@PathVariable Integer status,Long id) {
        categoryService.updateStatus(status,id);
        return Result.success();
    }

    @PostMapping()
    @Log("分类添加")
    public Result<String> add(@RequestBody CategoryDTO categoryDTO) {
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @Log("分类删除")
    public Result<String> delete(Long id) {
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    @Log("分类专类列表")
    public Result<List<Category>> list(Integer type) {
        return Result.success(categoryService.list(type));
    }

}
