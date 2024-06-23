package com.EmployeeService.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDto
{
    private String message;
    private HttpStatus httpStatus;
}
