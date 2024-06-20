package com.DepartmentService.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMesage
{
    private String message;
    private HttpStatus httpStatus;
}
