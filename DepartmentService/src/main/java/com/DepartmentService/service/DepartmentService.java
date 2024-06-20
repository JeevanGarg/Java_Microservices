package com.DepartmentService.service;

import com.DepartmentService.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService
{
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto findDepartmentById(Long id);
    List<DepartmentDto> getAllDepartmentDetails();
    DepartmentDto updateDepartment(DepartmentDto departmentDto,Long id);
    void deleteDepartment(Long id);
}
