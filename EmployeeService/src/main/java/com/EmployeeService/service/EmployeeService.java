package com.EmployeeService.service;

import com.EmployeeService.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService
{
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long empId);
    EmployeeDto updateEmployee(EmployeeDto employeeDto,Long empId);
    List<EmployeeDto> getAllEmployee();
    void deleteEmployee(Long empId);
}
