package com.DepartmentService.service.impl;

import com.DepartmentService.dto.DepartmentDto;
import com.DepartmentService.entity.Department;
import com.DepartmentService.exception.DepartmentNoFoundException;
import com.DepartmentService.repository.DepartmentRepository;
import com.DepartmentService.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService
{
    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto)
    {
        Department department = this.modelMapper.map(departmentDto, Department.class);
        Department saveDepartment = departmentRepository.save(department);
        DepartmentDto departmentDto1 = this.modelMapper.map(saveDepartment, DepartmentDto.class);
        return departmentDto1;
    }

    @Override
    public DepartmentDto findDepartmentById(Long id)
    {
        Optional<Department> optionalDepartment = this.departmentRepository.findById(id);
        if(optionalDepartment.isEmpty())
        {
            throw new DepartmentNoFoundException("Cannot found Department By departmentId");
        }

        Department department = optionalDepartment.get();
        DepartmentDto departmentDto = this.modelMapper.map(department, DepartmentDto.class);
        return departmentDto;
    }

    @Override
    public List<DepartmentDto> getAllDepartmentDetails()
    {
        List<Department> departmentList = this.departmentRepository.findAll();
        List<DepartmentDto> departmentDtoList = departmentList.stream().map(x -> modelMapper.map(x, DepartmentDto.class)).collect(Collectors.toList());

        return departmentDtoList;
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto, Long id)
    {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(optionalDepartment.isEmpty())
        {
            throw new DepartmentNoFoundException("Cannot found department by its Id");
        }
        Department department = optionalDepartment.get();
        department.setDepartmentCode(departmentDto.getDepartmentCode());
        department.setDepartmentName(department.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        department.setId(department.getId());

        Department department1 = this.departmentRepository.save(department);
        DepartmentDto departmentDto1 = this.modelMapper.map(department1, DepartmentDto.class);
        return departmentDto1;
    }

    @Override
    public void deleteDepartment(Long id)
    {
        Optional<Department> optionalDepartment = this.departmentRepository.findById(id);
        if(optionalDepartment.isEmpty())
        {
            throw new DepartmentNoFoundException("Department Not found By DepartmentId");
        }

        Department department = optionalDepartment.get();
        this.departmentRepository.delete(department);
    }
}
