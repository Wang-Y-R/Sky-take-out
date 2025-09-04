package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CategoryMapper {
    /**
     * 分页查询
     */
    Page<Category> pageSearch(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 查找
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category getById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Category category);


    @AutoFill(OperationType.INSERT)
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void add(Category category);

//    @Select("select * from category where type = #{type}")
    List<Category> list(Integer type);

    @Delete("delete from category where id = #{id}")
    void delete(Long id);
}
