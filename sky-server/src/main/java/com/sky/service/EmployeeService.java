package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    void update(EmployeeDTO employeeDTO);

    PageResult pageSearch(EmployeePageQueryDTO employeePageQueryDTO);

    Employee search(Long id);

    void updateStatus(Integer status,Long id);

    void editPassword(PasswordEditDTO passwordEditDTO);
}
