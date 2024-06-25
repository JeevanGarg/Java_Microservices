package com.DepartmentService.controller;

import com.DepartmentService.dto.DepartmentDto;
import com.DepartmentService.entity.Department;
import com.DepartmentService.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DepartmentControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private ObjectMapper objectMapper;

    private Department department;

    private DepartmentDto departmentDto;

    @BeforeEach
    public void setUp()
    {
        department=Department.builder().departmentName("IT").id(1L)
                .departmentDescription("Computer Science")
                .departmentCode("101").build();

        departmentDto=DepartmentDto.builder().departmentName("IT").id(1L)
                .departmentDescription("Computer Science")
                .departmentCode("101").build();
    }

    @Test
    public void createDepartmentTest() throws Exception {
        BDDMockito.given(departmentService.createDepartment(any(DepartmentDto.class))).willReturn(departmentDto);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDto))
        );


        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.departmentCode",is(department.getDepartmentCode())))
                .andExpect(jsonPath("$.departmentName",is(department.getDepartmentName())));
    }

    @Test
    public void getDepartmentByIdTest() throws Exception {
        BDDMockito.given(departmentService.findDepartmentById(department.getId())).willReturn(departmentDto);
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/department/{deptId}",department.getId()));



        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentCode",is(department.getDepartmentCode())))
                .andExpect(jsonPath("$.departmentName",is(department.getDepartmentName())));
    }

    @Test
    public void getAllDepartment() throws Exception {
        DepartmentDto departmentDto1=DepartmentDto.builder().id(2L).departmentName("HR")
                        .departmentDescription("Human Resource").departmentCode("102").build();

        List<DepartmentDto> departmentDtoList=new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto1);
        BDDMockito.given(departmentService.getAllDepartmentDetails()).willReturn(departmentDtoList);

        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/department"));



        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(departmentDtoList.size())));
    }

    @Test
    public void deleteDepartmentTest() throws Exception {
        BDDMockito.willDoNothing().given(departmentService).deleteDepartment(department.getId());
        ResultActions resultActions = mockMvc
                .perform(delete("/api/v1/department/{deptId}",department.getId()));



        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateDepartmentTest() throws Exception
    {
        BDDMockito.given(departmentService.updateDepartment(departmentDto,department.getId())).willReturn(departmentDto);
        ResultActions resultActions = mockMvc
                .perform(put("/api/v1/department/{deptId}",department.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto)));



        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

}
