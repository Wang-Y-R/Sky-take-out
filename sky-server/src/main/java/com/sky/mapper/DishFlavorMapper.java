package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    @Insert("insert into dish_flavor(dish_id, name, value) values (#{dishId},#{name},#{value})")
    void add(DishFlavor dishFlavor);

    void addPatch(List<DishFlavor> flavors);

    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> findByDishId(Long dishId);

    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);
}
