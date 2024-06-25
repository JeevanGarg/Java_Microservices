package com.DepartmentService.service;

import com.DepartmentService.dto.DepartmentDto;
import com.DepartmentService.entity.Department;
import com.DepartmentService.exception.DepartmentNoFoundException;
import com.DepartmentService.repository.DepartmentRepository;
import com.DepartmentService.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest
{
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    private DepartmentDto departmentDto;

    @BeforeEach
    public void setup()
    {
        department=Department.builder()
                .departmentName("IT").departmentDescription("Computer Science")
                .departmentCode("101").build();

        departmentDto=DepartmentDto.builder()
                .departmentName("IT").departmentDescription("Computer Science")
                .departmentCode("101").build();
    }

    @DisplayName("Testing create Department")
    @Test
    public void createDepartmentTest()
    {
        doReturn(department).when(modelMapper).map(departmentDto,Department.class);
        doReturn(departmentDto).when(modelMapper).map(department,DepartmentDto.class);
        given(departmentRepository.save(department)).willReturn(department);
        DepartmentDto departmentDto1 = departmentService.createDepartment(departmentDto);

        Assertions.assertNotNull(departmentDto1);
    }

    @DisplayName("Testing find Department By Id")
    @Test
    public void findDepartmentByIdTest()
    {
        doReturn(departmentDto).when(modelMapper).map(department,DepartmentDto.class);
        given(departmentRepository.findById(department.getId())).willReturn(Optional.ofNullable(department));
        DepartmentDto departmentDto1 = departmentService.findDepartmentById(department.getId());

        Assertions.assertNotNull(departmentDto1);
    }

    @DisplayName("Testing find Department By Id throws Exception")
    @Test
    public void findDepartmentByIdThrowsTest()
    {

        given(departmentRepository.findById(department.getId())).willReturn(Optional.empty());
        Assertions.assertThrows(DepartmentNoFoundException.class,()->{
            departmentService.findDepartmentById(department.getId());
        });
    }

    @DisplayName("Test to get All department List")
    @Test
    public void getAllDepartmentTest()
    {
        Department department1=Department.builder().departmentName("HR").departmentCode("102")
                .departmentDescription("human resource department").build();

        List<Department> departmentList=new ArrayList<>();
        departmentList.add(department);
        departmentList.add(department1);

        given(departmentRepository.findAll()).willReturn(departmentList);
        List<DepartmentDto> departmentDtoList = departmentService.getAllDepartmentDetails();
        Assertions.assertEquals(2,departmentDtoList.size());
    }


    @Test
    public void deleteDepartmentTest()
    {
        willDoNothing().given(departmentRepository).delete(department);
        given(departmentRepository.findById(department.getId())).willReturn(Optional.ofNullable(department));
        departmentService.deleteDepartment(department.getId());
        verify(departmentRepository,times(1)).delete(department);
    }

    @Test
    public void deleteDepartmentThrowsTest()
    {
        given(departmentRepository.findById(department.getId())).willReturn(Optional.ofNullable(null));
        Assertions.assertThrows(DepartmentNoFoundException.class,()->
                departmentService.deleteDepartment(department.getId()));
    }

    @Test
    public void updateDepartmentThrowsTest()
    {
        given(departmentRepository.findById(department.getId())).willReturn(Optional.ofNullable(null));
        Assertions.assertThrows(DepartmentNoFoundException.class,()->
                departmentService.updateDepartment(departmentDto,department.getId()));
    }

    @DisplayName("Test to update Department")
    @Test
    public void updateDepartmentTest()
    {
        department.setDepartmentName("GST");
        departmentDto.setDepartmentName("GST");
        doReturn(departmentDto).when(modelMapper).map(department,DepartmentDto.class);
        given(departmentRepository.save(department)).willReturn(department);
        given(departmentRepository.findById(department.getId())).willReturn(Optional.ofNullable(department));
        DepartmentDto departmentDto1 = departmentService.updateDepartment(departmentDto, department.getId());
        Assertions.assertEquals("GST",departmentDto1.getDepartmentName());
    }

}
