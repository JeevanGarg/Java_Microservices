package com.DepartmentService.exception;

import com.DepartmentService.dto.ApiResponseMesage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler
    public ResponseEntity<ApiResponseMesage> departmentNotFoundException(DepartmentNoFoundException ex)
    {
        ApiResponseMesage apiResponseMesage=ApiResponseMesage.builder().message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponseMesage,HttpStatus.NOT_FOUND);
    }
}
