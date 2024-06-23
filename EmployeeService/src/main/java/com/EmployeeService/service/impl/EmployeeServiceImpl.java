package com.EmployeeService.service.impl;

import com.EmployeeService.dto.EmployeeDto;
import com.EmployeeService.entity.Employee;
import com.EmployeeService.exception.EmployeeException;
import com.EmployeeService.repository.EmployeeRepository;
import com.EmployeeService.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto)
    {
        Employee employee = this.modelMapper.map(employeeDto, Employee.class);
        Employee employee1 = employeeRepository.save(employee);
        EmployeeDto employeeDto1 = this.modelMapper.map(employee1, EmployeeDto.class);
        return employeeDto1;
    }

    @Override
    public EmployeeDto getEmployeeById(Long empId)
    {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(empId);
        if(optionalEmployee.isEmpty())
        {
            throw new EmployeeException("Cannot found Employee By Id");
        }
        Employee employee = optionalEmployee.get();
        Employee employee1 = employeeRepository.save(employee);
        EmployeeDto employeeDto = modelMapper.map(employee1, EmployeeDto.class);
        return employeeDto;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long empId)
    {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if(optionalEmployee.isEmpty())
        {
            throw new EmployeeException("Cannot found employee By its EmpId");
        }

        Employee employee = optionalEmployee.get();
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());

        Employee employee1 = employeeRepository.save(employee);
        EmployeeDto employeeDto1 = this.modelMapper.map(employee1, EmployeeDto.class);
        return employeeDto1;
    }

    @Override
    public List<EmployeeDto> getAllEmployee()
    {
        List<Employee> employeeList = this.employeeRepository.findAll();
        List<EmployeeDto> employeeDtoList = employeeList.stream().map(x -> modelMapper.map(x, EmployeeDto.class)).collect(Collectors.toList());

        return employeeDtoList;
    }

    @Override
    public void deleteEmployee(Long empId)
    {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if(optionalEmployee.isEmpty())
        {
            throw new EmployeeException("Cannot found Employee By Id");
        }
        Employee employee = optionalEmployee.get();
        employeeRepository.delete(employee);
    }
}
