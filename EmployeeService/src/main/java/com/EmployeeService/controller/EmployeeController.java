package com.EmployeeService.controller;

import com.EmployeeService.dto.ApiResponseDto;
import com.EmployeeService.dto.EmployeeDto;
import com.EmployeeService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController
{
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto)
    {
        EmployeeDto employee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployeeDetails()
    {
        List<EmployeeDto> allEmployee = employeeService.getAllEmployee();
        return new ResponseEntity<>(allEmployee,HttpStatus.OK);
    }

    @GetMapping("{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("empId") Long empId)
    {
        EmployeeDto employeeById = employeeService.getEmployeeById(empId);
        return new ResponseEntity<>(employeeById,HttpStatus.OK);
    }

    @PutMapping("{empId}")
    public ResponseEntity<EmployeeDto> updateEmployee(EmployeeDto employeeDto,Long empId)
    {
        EmployeeDto employeeDto1 = employeeService.updateEmployee(employeeDto, empId);
        return new ResponseEntity<>(employeeDto1,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDto> deleteEmployee(Long empId)
    {
        employeeService.deleteEmployee(empId);
        ApiResponseDto apiResponseDto=ApiResponseDto.builder().message("Employee Deleted Successfully")
                .httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseDto,HttpStatus.OK);
    }
}
