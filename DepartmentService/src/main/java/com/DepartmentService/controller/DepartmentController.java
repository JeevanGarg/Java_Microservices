package com.DepartmentService.controller;

import com.DepartmentService.dto.ApiResponseMesage;
import com.DepartmentService.dto.DepartmentDto;
import com.DepartmentService.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController
{
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto)
    {
        DepartmentDto departmentDto1 = this.departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(departmentDto1, HttpStatus.CREATED);
    }

    @GetMapping("{deptId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("deptId") Long deptId)
    {
        DepartmentDto departmentById = this.departmentService.findDepartmentById(deptId);
        return new ResponseEntity<>(departmentById,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartment()
    {
        List<DepartmentDto> allDepartmentDetails = this.departmentService.getAllDepartmentDetails();
        return new ResponseEntity<>(allDepartmentDetails,HttpStatus.OK);
    }

    @PutMapping("{deptId}")
    public ResponseEntity<DepartmentDto> updateDepartment(DepartmentDto departmentDto,
                                                          @PathVariable("deptId") Long deptId)
    {
        DepartmentDto departmentDto1 = this.departmentService.updateDepartment(departmentDto, deptId);
        return new ResponseEntity<>(departmentDto1,HttpStatus.OK);
    }

    @DeleteMapping("{deptId}")
    public ResponseEntity<ApiResponseMesage> deleteDepartment(@PathVariable("deptId") Long deptId)
    {
        this.departmentService.deleteDepartment(deptId);
        ApiResponseMesage apiResponseMesage=ApiResponseMesage.builder().message("Department Deleted Succesfully")
                .httpStatus(HttpStatus.OK).build();

        return new ResponseEntity<>(apiResponseMesage,HttpStatus.OK);
    }
}
