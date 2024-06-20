package com.DepartmentService.exception;

public class DepartmentNoFoundException extends RuntimeException
{
    public DepartmentNoFoundException(String msg)
    {
        super(msg);
    }
}
